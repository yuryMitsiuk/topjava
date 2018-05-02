package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.Util.orElse;

@Controller
@RequestMapping("/meals")
public class JspMealController {

    @Autowired
    private MealService mealService;

    @GetMapping
    public String meals(Model model) {
        model.addAttribute("meals",
                MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("meal", mealService.get(id, AuthorizedUser.id()));
        return "mealForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        mealService.delete(id, AuthorizedUser.id());
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @PostMapping
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (!request.getParameter("id").isEmpty()) {
            meal.setId(Integer.valueOf(request.getParameter("id")));
            mealService.update(meal, AuthorizedUser.id());
        } else {
            mealService.create(meal, AuthorizedUser.id());
        }
        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                         @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                         @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                         @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
                         Model model) {
        List<Meal> meals = mealService.getBetweenDates(
                orElse(startDate, DateTimeUtil.MIN_DATE), orElse(endDate, DateTimeUtil.MAX_DATE), AuthorizedUser.id());
        model.addAttribute("meals", MealsUtil.getFilteredWithExceeded(meals, AuthorizedUser.getCaloriesPerDay(),
                orElse(startTime, LocalTime.MIN), orElse(endTime, LocalTime.MAX)));
        return "/meals";
    }

}
