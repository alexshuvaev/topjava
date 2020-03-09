package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(Profiles.JPA)
public class MealServiceJpaTest extends MealServiceTest {

    @Override
    public void delete() throws Exception {
        super.delete();
    }

    @Override
    public void deleteNotFound() throws Exception {
        super.deleteNotFound();
    }

    @Override
    public void deleteNotOwn() throws Exception {
        super.deleteNotOwn();
    }

    @Override
    public void create() throws Exception {
        super.create();
    }

    @Override
    public void get() throws Exception {
        super.get();
    }

    @Override
    public void getNotFound() throws Exception {
        super.getNotFound();
    }

    @Override
    public void getNotOwn() throws Exception {
        super.getNotOwn();
    }

    @Override
    public void update() throws Exception {
        super.update();
    }

    @Override
    public void updateNotFound() throws Exception {
        super.updateNotFound();
    }

    @Override
    public void getAll() throws Exception {
        super.getAll();
    }

    @Override
    public void getBetweenInclusive() throws Exception {
        super.getBetweenInclusive();
    }

    @Override
    public void getBetweenWithNullDates() throws Exception {
        super.getBetweenWithNullDates();
    }
}
