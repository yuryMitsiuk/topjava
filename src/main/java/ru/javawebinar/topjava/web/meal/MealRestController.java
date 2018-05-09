package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(MealRestController.MEAL_REST_URL)
public class MealRestController extends AbstractMealController {

    static final String MEAL_REST_URL = "/rest/profile/meals";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MEAL_REST_URL+"/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
                                           @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        return super.getBetween(startDateTime.toLocalDate(), startDateTime.toLocalTime(), endDateTime.toLocalDate(), endDateTime.toLocalTime());
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@RequestParam(value = "fromdate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate startdate,
                                           @RequestParam(value = "fromtime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime starttime,
                                           @RequestParam(value = "todate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate enddate,
                                           @RequestParam(value = "totime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endtime) {
        return super.getBetween(startdate, starttime, enddate, endtime);
    }

    /**
     * Filter uses custom formatter
     */
    @GetMapping(value = "/customfilter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> customFilter(@RequestParam(value = "fromdate", required = false)  LocalDate fromdate,
                                             @RequestParam(value = "fromtime", required = false) LocalTime fromtime,
                                             @RequestParam(value = "todate", required = false) LocalDate todate,
                                             @RequestParam(value = "totime", required = false) LocalTime totime) {
        return super.getBetween(fromdate, fromtime, todate, totime);
    }
}