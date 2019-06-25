package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_ID_1, USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_ID_1, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL3, MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> betweenDates = service.getBetweenDates(LocalDate.of(2019, 6, 20), LocalDate.of(2019, 6, 20), USER_ID);
        assertMatch(betweenDates, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> betweenDateTimes = service.getBetweenDateTimes(
                LocalDateTime.of(2019, 6, 20, 9, 15),
                LocalDateTime.of(2019, 6, 20, 12, 30), USER_ID);
        assertMatch(betweenDateTimes, MEAL2, MEAL1);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_ID_1, LocalDateTime.of(2019, Month.JUNE, 20, 9, 15), "завтрак", 500);
        updated.setId(MEAL_ID_1);
        updated.setDateTime(LocalDateTime.of(2018, Month.JUNE, 20, 9, 15));
        updated.setDescription("обед");
        updated.setCalories(100);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID_1, USER_ID), updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2019, Month.MAY, 30, 10, 0), "Завтрак", 500);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.get(created.getId(), USER_ID), newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        service.create(new Meal(null, LocalDateTime.of(2019, Month.JUNE, 20, 9, 15), "завтрак2", 502), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteFromAnotherUser() throws Exception {
        service.delete(MEAL_ID_1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getFromAnotherUser() throws Exception {
        service.get(MEAL_ID_1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateFromAnotherUser() {
        Meal updated = new Meal(MEAL_ID_1, LocalDateTime.of(2019, Month.JUNE, 20, 9, 15), "завтрак", 500);
        updated.setId(MEAL_ID_1);
        updated.setDateTime(LocalDateTime.of(2018, Month.JUNE, 20, 9, 15));
        updated.setDescription("обед");
        updated.setCalories(100);
        service.update(updated, ADMIN_ID);
    }

}