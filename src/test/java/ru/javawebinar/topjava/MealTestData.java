package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final LocalDateTime start = LocalDateTime.of(2018, Month.MARCH, 20, 9, 0);
    public static final LocalDateTime end = LocalDateTime.of(2018, Month.MARCH, 20, 11, 0);

    public static final Meal USER_MEAL1 = new Meal(START_SEQ + 2, LocalDateTime.of(2018, Month.MARCH, 20, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL2 = new Meal(START_SEQ + 3, LocalDateTime.of(2018, Month.MARCH, 20, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL3 = new Meal(START_SEQ + 4, LocalDateTime.of(2018, Month.MARCH, 20, 20, 0), "Ужин", 500);
    public static final Meal ADMIN_MEAL4 = new Meal(START_SEQ + 5, LocalDateTime.of(2018, Month.MARCH, 21, 10, 0), "Завтрак", 1000);
    public static final Meal ADMIN_MEAL5 = new Meal(START_SEQ + 6, LocalDateTime.of(2018, Month.MARCH, 21, 13, 0), "Обед", 500);
    public static final Meal ADMIN_MEAL6 = new Meal(START_SEQ + 7, LocalDateTime.of(2018, Month.MARCH, 21, 20, 0), "Ужин", 510);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "datetime");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("datetime").isEqualTo(expected);
    }
}
