package ru.javawebinar.topjava.service.user;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles("datajpa")
public class UserServiceDataJpaTest extends UserServiceTest {
    @Test
    public void getWithMeals() throws Exception {
        User user = service.getMeals(USER_ID);
        assertMatch(user, USER);
        MealTestData.assertMatch(user.getMeals(), MealTestData.MEALS);
    }
}