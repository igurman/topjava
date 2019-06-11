package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemoryImpl implements MealDao {

    private CopyOnWriteArrayList<Meal> meals;
    private AtomicInteger idCount = new AtomicInteger(0);

    public MealDaoInMemoryImpl() {
        this.meals = new CopyOnWriteArrayList<>(MealsUtil.getList());
        idCount.set(this.meals.size());
    }

    @Override
    public void create(Meal meal) {
        meal.setId(idCount.incrementAndGet());
        meals.addIfAbsent(meal);
    }

    @Override
    public void update(Meal meal) {
        int i = meals.indexOf(this.getById(meal.getId()));
        if(i != -1) meals.set(i, meal);
    }

    @Override
    public void remove(int id) {
        meals.removeIf(a -> a.getId() == id);
    }

    @Override
    public Meal getById(int id) {
        for(Meal meal :meals) {
            if (meal.getId() == id) return meal;
        }
        return null;
    }

    @Override
    public List<Meal> list() {
        return meals;
    }
}
