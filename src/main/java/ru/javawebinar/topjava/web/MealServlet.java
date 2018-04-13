package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealCRUD;
import ru.javawebinar.topjava.repository.MealCRUDImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(UserServlet.class);
    private MealCRUD mealCRUD;

    @Override
    public void init() throws ServletException {
        super.init();
        mealCRUD = new MealCRUDImpl();
        MealsUtil.meals.stream().forEach(m->mealCRUD.create(m));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = req.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                delete(req, resp);
                break;
            case "new":
                create(req, resp);
                break;
            case "update":
                update(req, resp);
                break;
            default:
                getAll(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(getId(req),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));

        if (getId(req) == null) {
            mealCRUD.create(meal);
        } else {
            mealCRUD.update(meal);
        }

        resp.sendRedirect("meals");
    }

    private Integer getId(HttpServletRequest req) {
        String sId = req.getParameter("id");
        return sId.isEmpty() ? null : Integer.valueOf(sId);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = getId(req);
        log.info("Delete meal id={}", id);
        mealCRUD.delete(id);
        resp.sendRedirect("meals");
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Create meal");
        final Meal meal = new Meal(LocalDateTime.now().withNano(0), "", 1000);
        req.setAttribute("meal", meal);
        req.getRequestDispatcher("meal.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getId(req);
        log.info("Update meal id={}", id);
        final Meal meal = mealCRUD.get(Integer.valueOf(id));
        req.setAttribute("meal", meal);
        req.getRequestDispatcher("meal.jsp").forward(req, resp);

    }

    private void getAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("All");
        req.setAttribute("meals", MealsUtil.getMealsWithExceed(mealCRUD.getAll()));
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
