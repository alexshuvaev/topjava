package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            log.info("save new meal={}, userId={}", meal, userId);
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        Meal updatingMeal = get(meal.getId(), userId);
        if (updatingMeal != null && userId == meal.getUserId() && userId == updatingMeal.getUserId()) {
            log.info("update meal={}, userId={}", updatingMeal, userId);
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete meal: id={}, userId={}", id, userId);
        return repository.entrySet().removeIf(entry ->
                id == entry.getKey() && userId == entry.getValue().getUserId());
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal: id={}, userId={}", id, userId);
        return repository.entrySet().stream()
                .filter(meal -> meal.getValue().getUserId() == userId)
                .filter(meal -> meal.getKey() == id)
                .map(Map.Entry::getValue)
                .findAny()
                .orElse(null);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("get all meal with userId={}", userId);
        return repository.values().stream()
                .filter(meal -> userId == meal.getUserId())
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

