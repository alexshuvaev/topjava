package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Map<Integer, HashMap<Integer, Meal>> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            log.info("save new meal={}, userId={}", meal, userId);
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            if (repository.containsKey(userId)) {
                repository.get(userId).put(meal.getId(), meal);
            } else {
                HashMap<Integer, Meal> theMeal = new HashMap<>();
                theMeal.put(meal.getId(), meal);
                repository.put(userId, theMeal);
            }
            return meal;
        }

        log.info("update meal={}, userId={}", meal, userId);
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        log.info("delete meal: id={}, userId={}", id, userId);
        return repository.get(userId).values()
                .removeIf(meal -> meal.getId().equals(id));
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        log.info("get meal: id={}, userId={}", id, userId);
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        log.info("get all meal with userId={}", userId);
        return repository.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getFilteredByDate(Integer userId, LocalDate startDate, LocalDate endDate) {
        return repository.get(userId).values().stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

