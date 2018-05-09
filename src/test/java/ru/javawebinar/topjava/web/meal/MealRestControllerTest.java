package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.Month;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String MEAL_REST_URL = MealRestController.MEAL_REST_URL+"/";

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(MEAL_REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.contentJson(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1)));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(MEAL_REST_URL+MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.contentJson(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(MEAL_REST_URL+MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(UserTestData.USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updatedMeal = new Meal(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        updatedMeal.setDescription("Завтрак обновленный");
        updatedMeal.setCalories(450);
        mockMvc.perform(put(MEAL_REST_URL+MEAL1_ID).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeIgnoreProps(updatedMeal)))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(mealService.getAll(UserTestData.USER_ID),  MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, updatedMeal);

    }

    @Test
    public void testCreate() throws Exception {
        Meal createdMeal = new Meal(LocalDateTime.of(2018, Month.MAY, 9, 17, 0), "Ужин", 755);
        ResultActions resultActions = mockMvc.perform(post(MEAL_REST_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(createdMeal)))
                .andDo(print())
                .andExpect(status().isCreated());

        Meal returnedMeal = TestUtil.readFromJson(resultActions, Meal.class);
        createdMeal.setId(returnedMeal.getId());
        assertMatch(returnedMeal, createdMeal);
        assertMatch(mealService.getAll(UserTestData.USER_ID), createdMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(MEAL_REST_URL+"between?from="+
                MEAL1.getDateTime().minusHours(1L)
                        +"&to="+
                MEAL1.getDateTime().plusHours(5L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.contentJson(MEAL2, MEAL1));
    }
}