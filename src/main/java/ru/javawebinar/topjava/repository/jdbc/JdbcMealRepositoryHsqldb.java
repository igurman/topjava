package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Profile("hsqldb")
@Repository
public class JdbcMealRepositoryHsqldb extends JdbcMealRepository {

    @Override
    protected Timestamp modifyTime(LocalDateTime ldt){
        return Timestamp.valueOf(ldt);
    }
}