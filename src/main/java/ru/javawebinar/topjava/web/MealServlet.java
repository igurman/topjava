package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealDao mealDao;
    private DateTimeFormatter formatter;

    @Override
    public void init() {
        mealDao = new MealDaoInMemoryImpl();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null) {
            if (action.equals("delete")) {
                String id = request.getParameter("id");
                if (id != null) {
                    mealDao.remove(Integer.parseInt(id));
                    response.sendRedirect("meals");
                    return;
                }
            }
            if (action.equals("edit")) {
                String id = request.getParameter("id");
                if (id != null) {
                    request.setAttribute("meal", mealDao.getById(Integer.parseInt(id)));
                }else{
                    request.setAttribute("meal", null);
                }
                request.getRequestDispatcher("/meals-edit.jsp").forward(request, response);
                return;
            }
        }
        List<MealTo> mealTo = MealsUtil.getFilteredWithExcess(mealDao.list(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("localDateTimeFormat", formatter);
        request.setAttribute("listMeals", mealTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        String date = req.getParameter("date");
        String time = req.getParameter("time");
        String name = req.getParameter("name");
        String calories = req.getParameter("calories");

        if (isFull(action) && isFull(date) && isFull(time) && isFull(name) && isFull(calories)) {
            Meal meal = new Meal(
                    0,
                    LocalDateTime.parse(date + " " + time, formatter),
                    name,
                    Integer.parseInt(calories)
            );

            if (action.equals("create")) {
                mealDao.create(meal);
            }

            if (action.equals("update")) {
                String id = req.getParameter("id");
                if (isFull(id)) {
                    meal.setId(Integer.parseInt(id));
                    mealDao.update(meal);
                }
            }
        }
        resp.sendRedirect("meals");
    }

    private boolean isFull(String t){
        return t != null && !t.equals("");
    }

}
