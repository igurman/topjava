package ru.javawebinar.topjava.service.meal;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles("datajpa")
public class MealServiceDataJpaTest extends MealServiceTest {
    @Test
    public void getWithMeals() throws Exception {
        Meal meal = service.getWithUser(MEAL1_ID, USER_ID);
        assertMatch(meal, MEAL1);
        UserTestData.assertMatch(meal.getUser(), UserTestData.USER);
    }
}