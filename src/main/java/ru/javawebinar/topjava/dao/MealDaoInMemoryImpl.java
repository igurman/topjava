package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemoryImpl implements MealDao {

    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private static AtomicInteger idCount = new AtomicInteger(0);

    public MealDaoInMemoryImpl() {
//        MealsUtil.getList().forEach(this::create);

        MealsUtil.getList().forEach(meal -> {
            int id = meal.getId();
            if (id > idCount.get()) idCount.set(id);
            meals.put(id, meal);
        });
    }

    @Override
    public void create(Meal meal) {
        int id = idCount.incrementAndGet();
        meal.setId(id);
        meals.put(id, meal);
    }

    @Override
    public void update(Meal meal) {
        meals.put(meal.getId(), meal);
    }

    @Override
    public void remove(int id) {
        meals.remove(id);
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> list() {
        return new ArrayList<>(meals.values());
    }
}
