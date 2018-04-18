package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal, int userID) {
        return repository.save(meal, userID);
    }

    @Override
    public void delete(int id, int userID) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userID), id);

    }

    @Override
    public Meal get(int id, int userID) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userID), id);
    }

    @Override
    public Meal update(Meal meal, int userID) {
        int id = meal.getId();
        checkNotFoundWithId(repository.get(id, userID), id);
        return save(meal, userID);
    }

    @Override
    public List<Meal> getAll(int userID) {
        return new ArrayList<>(repository.getAll(userID));
    }
}