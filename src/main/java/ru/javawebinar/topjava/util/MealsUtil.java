package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 10, 0), "Завтрак", 500, 1),
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 13, 0), "Обед", 1000,1),
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 20, 0), "Еда", 500,1),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100,1),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 30), "Обед", 1000,1),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 19, 0), "Ужин", 500,1),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 29, 23, 59), "Ужин", 410,2),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 7, 0), "Завтрак", 410,2),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 11, 0), "Обед", 410,2),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 410,2),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 12, 0), "Обед", 410,2)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filteredByStreams(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filteredByStreams(meals, caloriesPerDay, meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime));
    }

    public static List<MealTo> filteredByStreams(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
