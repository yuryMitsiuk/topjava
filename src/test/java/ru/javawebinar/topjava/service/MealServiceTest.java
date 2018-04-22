package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL1.getId(), USER_ID);
        assertMatch(meal, USER_MEAL1);
    }

    @Test
    public void delete() {
        service.delete(ADMIN_MEAL4.getId(), ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_MEAL6, ADMIN_MEAL5);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> betweenDates = service.getBetweenDates(start.toLocalDate(), end.toLocalDate(), USER_ID);
        assertMatch(betweenDates, USER_MEAL3, USER_MEAL2, USER_MEAL1);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> betweenDateTimes = service.getBetweenDateTimes(start, end.plusDays(1L), ADMIN_ID);
        assertMatch(betweenDateTimes, ADMIN_MEAL4);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, USER_MEAL3, USER_MEAL2, USER_MEAL1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(ADMIN_MEAL5);
        updated.setCalories(750);
        updated.setDescription("Updated admin meal");
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(ADMIN_MEAL5.getId(), ADMIN_ID), updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2018, Month.APRIL, 20, 16, 0), "New admin meal", 525);
        service.create(newMeal, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), newMeal, ADMIN_MEAL6, ADMIN_MEAL5, ADMIN_MEAL4);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(USER_MEAL1.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundGet() {
        service.get(USER_MEAL1.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() {
        Meal updated = new Meal(ADMIN_MEAL5);
        updated.setCalories(750);
        updated.setDescription("Updated admin meal");
        service.update(updated, USER_ID);
    }
}