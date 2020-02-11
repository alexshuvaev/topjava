package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class MealsUtil {

    public static List<MealTo> filteredByLoop(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        List<MealTo> list = new CopyOnWriteArrayList<>();
        for (Meal meal : meals) {
            if (TimeUtil.isBetweenInclusive(meal.getTime(), startTime, endTime)) {
                MealTo to = createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay);
                list.add(to);
            }
        }
        return list;
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
