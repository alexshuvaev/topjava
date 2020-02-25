package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final Meal MEAL_USER_1 = new Meal(100002, LocalDateTime.of(2020, 1, 30, 7, 30), "Завтрак", 500);
    public static final Meal MEAL_USER_2 = new Meal(100003, LocalDateTime.of(2020, 1, 30, 13, 0), "Обед", 750);
    public static final Meal MEAL_USER_3 = new Meal(100004, LocalDateTime.of(2020, 1, 30, 19, 0), "Ужин", 700);

    public static final Meal MEAL_USER_4 = new Meal(100005, LocalDateTime.of(2020, 1, 31, 7, 0), "Завтрак", 300);
    public static final Meal MEAL_USER_5 = new Meal(100006, LocalDateTime.of(2020, 1, 31, 12, 30), "Обед", 900);
    public static final Meal MEAL_USER_6 = new Meal(100007, LocalDateTime.of(2020, 1, 31, 18, 30), "Ужин", 700);

    public static final Meal MEAL_USER_FOR_NOT_FOUND = new Meal(100010, LocalDateTime.of(2020, 1, 31, 18, 30), "Ужин", 700);

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, 2, 1, 12, 0), "Новая еда", 800);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_USER_1);
        updated.setDescription("Обновленная еда");
        updated.setCalories(9);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("id").isEqualTo(expected);
    }
}
