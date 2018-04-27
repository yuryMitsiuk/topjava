package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;

@ActiveProfiles(value = Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getWithUser() throws Exception {
        Meal meal = service.getWithUser(MEAL1_ID ,UserTestData.USER_ID);
        assertMatch(meal, MEAL1);
        UserTestData.assertMatch(meal.getUser(), UserTestData.USER);
    }
}
