package ru.javawebinar.topjava.dao.impl;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemoryMealDaoImpl implements MealDao {
    private static List<Meal> mealList;

    public MemoryMealDaoImpl() {
        mealList = new CopyOnWriteArrayList<>(
                Arrays.asList(
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 5, 8, 0), "Завтрак", 200),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 5, 13, 30), "Обед", 500),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 5, 19, 0), "Ужин", 700),

                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 6, 8, 0), "Завтрак", 500),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 6, 11, 30), "Перекус", 200),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 6, 14, 0), "Обед", 700),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 6, 19, 0), "Ужин", 700),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 6, 21, 0), "Перед сном", 200),

                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 7, 10, 0), "Завтрак", 200),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 7, 14, 0), "Обед", 700),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 7, 19, 0), "Ужин", 500))
        );
    }

    @Override
    public List<Meal> getAll() {
        return mealList;
    }

    @Override
    public void add(Meal meal) {
        mealList.add(meal);
    }

    @Override
    public void delete(int mealId) {
        mealList.remove(mealId);
    }

    @Override
    public void update(Meal meal, int mealId) {
        mealList.set(mealId, meal);
    }
}
