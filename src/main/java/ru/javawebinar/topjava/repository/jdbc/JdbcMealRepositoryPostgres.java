package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Profile("postgres")
@Repository
public class JdbcMealRepositoryPostgres extends JdbcMealRepository {

    @Override
    protected LocalDateTime modifyTime(LocalDateTime ldt){
        return ldt;
    }
}