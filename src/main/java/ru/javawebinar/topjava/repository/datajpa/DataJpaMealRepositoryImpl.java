package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.transaction.NotSupportedException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal Meal, int userId) {
        if (!Meal.isNew() && get(Meal.getId(), userId) == null) {
            return null;
        }
        Meal.setUser(crudUserRepository.getOne(userId));
        return crudRepository.save(Meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findOne(id, userId).orElse(null);
//        return crudRepository.findOneByUserAndId(crudUserRepository.findById(userId).get(), id).orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAll(userId);
//        return crudRepository.findAllByUserOrderByDateTimeDesc(crudUserRepository.findById(userId).get());
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.findBetween(userId, startDate, endDate);
//        return crudRepository.findAllByUserAndDateTimeBetweenOrderByDateTimeDesc(crudUserRepository.findById(userId).get(), startDate, endDate);
    }

    @Override
    public Meal getWithUser(int id, int userId) {
        return crudRepository.getWithUser(id, userId);
    }
}
