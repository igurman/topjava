package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_ID_1 = START_SEQ + 2;

    public static final Meal MEAL1 = new Meal(START_SEQ + 2, LocalDateTime.of(2019, Month.JUNE, 20, 9, 15), "завтрак", 500);
    public static final Meal MEAL2 = new Meal(START_SEQ + 3, LocalDateTime.of(2019, Month.JUNE, 20, 12, 30), "обед", 1000);
    public static final Meal MEAL3 = new Meal(START_SEQ + 4, LocalDateTime.of(2019, Month.JUNE, 20, 19, 0), "ужин", 800);

    public static final Meal MEAL4 = new Meal(START_SEQ + 5, LocalDateTime.of(2019, Month.JUNE, 20, 9, 15), "завтрак", 500);
    public static final Meal MEAL5 = new Meal(START_SEQ + 6, LocalDateTime.of(2019, Month.JUNE, 20, 12, 30), "обед", 500);
    public static final Meal MEAL6 = new Meal(START_SEQ + 7, LocalDateTime.of(2019, Month.JUNE, 20, 19, 0), "ужин", 800);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
