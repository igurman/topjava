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

    public MealServlet() {
        super();
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
        }
        List<MealTo> mealTo = MealsUtil.getFilteredWithExcess(mealDao.list(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
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

        if (action != null && date != null && time != null && name != null && calories != null) {
            if (action.equals("create")) {
                mealDao.create(new Meal(
                                0,
                                LocalDateTime.parse(date + " " + time, formatter),
                                name,
                                Integer.parseInt(calories)
                        )
                );
            }

            if (action.equals("update")) {
                String id = req.getParameter("id");
                if (id != null) {
                    mealDao.update(new Meal(
                                    Integer.parseInt(id),
                                    LocalDateTime.parse(date + " " + time, formatter),
                                    name,
                                    Integer.parseInt(calories)
                            )
                    );
                }
            }
        }
        resp.sendRedirect("meals");
    }

}
