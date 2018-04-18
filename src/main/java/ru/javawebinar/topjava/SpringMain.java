package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            adminUserController.create(new User(null, "fserName", "email1", "password", Role.ROLE_ADMIN));
            adminUserController.create(new User(null, "aserName", "email", "password", Role.ROLE_ADMIN));
            System.out.println("---------------------------------------------------");
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll().stream().forEach(System.out::println);
            mealRestController.getAllFiltered(LocalDate.of(2015, 05 , 30), LocalDate.of(2015, 05 , 30), LocalTime.of(9, 0), LocalTime.of(11, 0))
                    .forEach(System.out::println);
            System.out.println("---------------------------------------------------");
            mealRestController.delete(4);
            mealRestController.getAll().stream().forEach(System.out::println);
        }
    }
}
