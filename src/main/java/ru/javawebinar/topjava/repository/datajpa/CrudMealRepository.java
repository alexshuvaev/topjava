package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    Optional<Meal> findByIdAndUserId(Integer id, Integer user_id);

    List<Meal> findAllByUserIdOrderByDateTimeDesc(Integer user_id);

    List<Meal> findAllByUserIdAndDateTimeGreaterThanEqualAndDateTimeLessThanOrderByDateTimeDesc(Integer user_id, @NotNull LocalDateTime dateTime, @NotNull LocalDateTime dateTime2);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Meal m WHERE m.id=?1 AND m.user.id =?2")
    int deleteByIdAndUserId(Integer id, Integer user_id);
}
