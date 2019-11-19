package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class JspMealController {
    @Autowired
    private MealService service;

    @GetMapping("/meals/delete/{id}")
    public String delete(@NotNull @PathVariable int id, HttpServletRequest request){
        service.delete(id, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/meals/create")
    public String create(Model model){
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("action", "create");
        model.addAttribute("meal", meal);
        return "/mealForm";
    }

    @PostMapping("/meals/create")
    public String createPost(HttpServletRequest request) throws UnsupportedEncodingException {
        Meal meal = createModel(request);
        service.create(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/meals/update/{id}")
    public String update(@NotNull @PathVariable int id, Model model){
        Meal meal = service.get(id, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        model.addAttribute("action", "update");
        return "/mealForm";
    }

    @PostMapping("/meals/update")
    public String updatePost(HttpServletRequest request) throws UnsupportedEncodingException {
        int id = Integer.parseInt(request.getParameter("id"));
        Meal meal = createModel(request);
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/meals/filter")
    public String update(HttpServletRequest request){
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> mealsDateFiltered = service.getBetweenDates(startDate, endDate, SecurityUtil.authUserId());
        List<MealTo> meals = MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        request.setAttribute("meals", meals);
        return "meals";
    }

    private Meal createModel(HttpServletRequest request){
        return new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
    }

}
