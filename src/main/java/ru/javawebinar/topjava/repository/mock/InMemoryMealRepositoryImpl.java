package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, HashMap<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.stream().limit(3).forEach(m->save(m, 1));
        MealsUtil.MEALS.stream().skip(3).forEach(m->save(m, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        HashMap<Integer, Meal> mealHashMap = repository.computeIfAbsent(userId, HashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealHashMap.put(meal.getId(), meal);
            return meal;
        }
        return mealHashMap.computeIfPresent(meal.getId(), (id, oldMeal)-> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.get(userId).remove(id, get(id, userId));
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(userId).getOrDefault(id, null);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).values()
                .stream().sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

