package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    List<Meal> getAll();
    void add(Meal meal);
    void delete(int mealId);
    void update(Meal meal, int mealId);
}
