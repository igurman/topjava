package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        getFilteredWithExceededToForeach(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> dateCaloriesSumm = mealList.stream()
                .collect(
                        Collectors.groupingBy(
                                t -> t.getDateTime().toLocalDate(),
                                Collectors.summingInt(UserMeal::getCalories)
                        )
                );

        return mealList.stream()
                .filter(t -> TimeUtil.isBetween(t.getDateTime().toLocalTime(), startTime, endTime))
                .map(t -> new UserMealWithExceed(
                        t.getDateTime(),
                        t.getDescription(),
                        t.getCalories(),
                        dateCaloriesSumm.get(t.getDateTime().toLocalDate()) > caloriesPerDay)
                )
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededToForeach(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> dateCaloriesSumm = new HashMap<>();
        for(UserMeal meal :mealList){
            dateCaloriesSumm.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExceed> result = new ArrayList<>();
        for(UserMeal meal :mealList){
            if(TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)){
                int calories = dateCaloriesSumm.get(meal.getDateTime().toLocalDate());
                result.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), calories > caloriesPerDay));
            }
        }

        return result;
    }
}
