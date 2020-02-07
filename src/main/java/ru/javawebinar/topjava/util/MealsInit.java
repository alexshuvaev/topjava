package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealsInit {
    private static MealsInit mealsInit;
    private List<Meal> mealsList;


    private MealsInit() {
        mealsList = Arrays.asList(
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
                new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 7, 19, 0), "Ужин", 500));
    }

    public static synchronized MealsInit getInstance() {
        if (mealsInit == null) mealsInit = new MealsInit();
        return mealsInit;
    }

    public List<Meal> getMealsList() {
        return mealsList;
    }
}
