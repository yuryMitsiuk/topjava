package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealCRUDImpl implements MealCRUD {

    private AtomicInteger id = new AtomicInteger();
    private Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();


    @Override
    public Meal get(int id) {
        return mealMap.getOrDefault(id, null);
    }

    @Override
    public Meal create(Meal meal) {
        int mId = id.incrementAndGet();
        meal.setId(mId);
        mealMap.putIfAbsent(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        int id = meal.getId();
        return mealMap.put(id, meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }
}
