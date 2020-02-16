package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Map<Integer, User> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    public InMemoryUserRepository() {
        List<User> users = Arrays.asList(
                new User(null,"Alex","alex@gmail.com","123456", Role.ROLE_ADMIN),
                new User(null,"Mark","caesar121@gmail.com","123456", 2500, true, Collections.singleton(Role.ROLE_USER)),
                new User(null,"Mark","mark@gmail.com","123456", 2500, true, Collections.singleton(Role.ROLE_USER)),
                new User(null,"Emma","emma@gmail.com","123456", 1800, true, Collections.singleton(Role.ROLE_USER)),
                new User(null,"Mia","mia@gmail.com","123456", 1300, true, Collections.singleton(Role.ROLE_USER)),
                new User(null,"Archie","archie@gmail.com","123456", 1800, true, Collections.singleton(Role.ROLE_USER)),
                new User(null,"Balthazar Gracian","balthazar.writer@gmail.com","123456", 2000, true, Collections.singleton(Role.ROLE_USER)),
                new User(null,"Felix Li","felix@gmail.com","123456", 2200, true, Collections.singleton(Role.ROLE_USER))
        );
        users.forEach(this::save);
    }

    @Override
    public boolean delete(Integer id) {
        log.info("delete user with id={}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save user={}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(Integer id) {
        log.info("get user with id={}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll users");
        return repository.values().stream()
                .sorted(Comparator.comparing(User::getName)
                        .thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream()
                .filter(line -> line.getEmail().equals(email))
                .findAny()
                .orElse(null);
    }
}
