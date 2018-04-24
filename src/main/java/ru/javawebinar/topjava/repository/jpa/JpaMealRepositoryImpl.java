package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {


    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && (get(meal.getId(), userId) == null)) {
            return null;
        }
        User user = manager.getReference(User.class, userId);
        meal.setUser(user);
        if (meal.isNew()) {
            manager.persist(meal);
            return meal;
        } else {
            return manager.merge(meal);
        }

    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return manager.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return DataAccessUtils.singleResult(manager.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .getResultList());
    }

    @Override
    public List<Meal> getAll(int userId) {
        return manager.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return manager.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter(1, userId)
                .setParameter(2, startDate)
                .setParameter(3, endDate)
                .getResultList();
    }
}