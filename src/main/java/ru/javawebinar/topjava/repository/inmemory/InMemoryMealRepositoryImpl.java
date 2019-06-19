package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        meal.setUserId(userId);
        // treat case: update, but absent in storage
        return  repository.computeIfPresent(meal.getId(), (id, oldMeal) -> oldMeal.getUserId() == userId ? meal : oldMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.remove(id, get(id, userId));
    }

    @Override
    public Meal get(int id, int userId) {
        Meal result = repository.get(id);
        return (result != null && userId == result.getUserId()) ? result : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getBetween(repository.values(), meal -> userId == meal.getUserId());
    }

    @Override
    public List<Meal> getBetween(LocalDate startDate, LocalDate endDate, int userId) {
        return getBetween(repository.values(), meal -> userId == meal.getUserId() && DateTimeUtil.isBetween(meal.getDate(), startDate, endDate));
    }

    private List<Meal> getBetween(Collection<Meal> meals, Predicate<Meal> filter) {
        return meals.parallelStream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

