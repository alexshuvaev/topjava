package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            log.info("save new meal={}, userId={}", meal, userId);

            HashMap<Integer, Meal> theMeal = new HashMap<>();
            theMeal.put(meal.getId(), meal);

            repository.putIfAbsent(userId, theMeal);
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        Map<Integer, Meal> mealMap = repository.get(userId);
        log.info("update meal={}, userId={}", meal, userId);
        return mealMap != null ? mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        log.info("delete meal: id={}, userId={}", id, userId);
        return repository.getOrDefault(userId, Collections.emptyMap()).values()
                .remove(id);
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        log.info("get meal: id={}, userId={}", id, userId);
        return repository.getOrDefault(userId, Collections.emptyMap()).get(id);
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        log.info("get all meals with userId={}", userId);
        return filter(repository.getOrDefault(userId, Collections.emptyMap()).values(), meal -> true);
    }

    @Override
    public List<Meal> getFilteredByDate(Integer userId, LocalDate startDate, LocalDate endDate) {
        log.info("get all filtered meals with userId={}", userId);
        return filter(repository.getOrDefault(userId, Collections.emptyMap()).values(),
                meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate));
    }

    private List<Meal> filter(Collection<Meal> meals, Predicate<Meal> filter) {
        return meals.stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

