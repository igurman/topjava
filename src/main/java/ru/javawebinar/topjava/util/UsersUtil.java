package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "Ann", "ann@gmail.com", "12345", Role.ROLE_USER),
            new User(null, "Tom", "tom@gmail.com", "rgg4y", Role.ROLE_USER),
            new User(null, "Zack", "zack@gmail.com", "gwe466", Role.ROLE_USER),
            new User(null, "Simon", "simon@gmail.com", "qwerty", Role.ROLE_USER),
            new User(null, "Lili", "lili@gmail.com", "3333445", Role.ROLE_ADMIN),
            new User(null, "Scott", "scott@gmail.com", "3245dfgvg", Role.ROLE_USER)
    );
}
