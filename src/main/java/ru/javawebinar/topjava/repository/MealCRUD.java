package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealCRUD {
    Meal get(int id);
    Meal create(Meal meal);
    void delete(int id);
    Meal update(Meal meal);
    List<Meal> getAll();
}
