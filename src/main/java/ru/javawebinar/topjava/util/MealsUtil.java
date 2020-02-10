package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealsUtil {

    public static List<MealTo> filteredByLoop(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        List<MealTo> list = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);
        for (Meal meal : meals) {
            if (TimeUtil.isBetweenInclusive(meal.getTime(), startTime, endTime)) {
                MealTo to = createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay);
                int id = counter.getAndIncrement();
                to.setId(id);
                list.add(to);
            }
        }
        counter.set(0);
        return list;
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
