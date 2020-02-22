package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration("classpath:spring/spring-all-module.xml")
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService service;

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL_USER_6, MEAL_USER_5, MEAL_USER_4, MEAL_USER_3, MEAL_USER_2, MEAL_USER_1);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> all = service.getBetweenHalfOpen(LocalDate.of(2020, 1, 30),
                                                    LocalDate.of(2020, 1, 30),
                                                    USER_ID);
        assertMatch(all, MEAL_USER_3, MEAL_USER_2, MEAL_USER_1);
    }

    @Test
    public void get() {
        Meal meal = service.get(100002, USER_ID);
        assertMatch(meal, MEAL_USER_1);
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(100002, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        service.delete(100002, USER_ID);
        service.get(100002, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteForeignMeal() {
        service.delete(100010, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getForeignMeal() {
        service.get(100010, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateForeignMeal() {
        Meal updated = getUpdated();
        service.update(updated, ADMIN_ID);
    }
}