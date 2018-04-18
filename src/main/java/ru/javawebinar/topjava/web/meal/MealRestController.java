package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public void delete(int id) {
        log.info("delete meal id = {}", id);
        service.delete(id, AuthorizedUser.id());
    }

    public Meal save(Meal meal) {
        checkNew(meal);
        log.info("save {}", meal);
        return service.save(meal, AuthorizedUser.id());
    }

    public Meal get(int id) {
        log.info("get meal id = {}", id);
        return service.get(id, AuthorizedUser.id());
    }

    public Meal update(Meal meal, int id) {
        log.info("update meal id = {}", id);
        checkNotFoundWithId(meal, id);
        return service.update(meal, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAllFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getAll with filter");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()).stream()
                .filter(m-> DateTimeUtil.filter(m.getDate(), startDate, endDate))
                .filter(m->DateTimeUtil.filter(m.getTime(), startTime, endTime))
                .collect(Collectors.toList()), AuthorizedUser.getCaloriesPerDay());
    }

}