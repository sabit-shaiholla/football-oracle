package com.oracle.football.User;

import com.oracle.football.AbstractTestcontainers;
import com.oracle.football.model.User;
import com.oracle.football.repository.UserJDBCAccessService;
import com.oracle.football.repository.UserRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private UserJDBCAccessService underTest;
    private final UserRowMapper userRowMapper = new UserRowMapper();

    @BeforeEach
    void setUp(){
        underTest = new UserJDBCAccessService(
                getJdbcTemplate(),
                userRowMapper
        );
    }

    @Test
    void selectAllUsers(){
        // Given
        User user = new User(
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.name().fullName(),
                "password");
        underTest.insertUser(user);

        // When
        List<User> actual = underTest.selectAllUsers();

        // Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectUserById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        User user = new User(
                email,
                FAKER.name().fullName(),
                "password");
        underTest.insertUser(user);

        int id = underTest.selectAllUsers()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .map(User::getId)
                .findFirst()
                .orElseThrow();

        // When
        Optional<User> actual = underTest.selectUserById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(u -> {
            assertThat(u.getId()).isEqualTo(user.getId());
            assertThat(u.getEmail()).isEqualTo(user.getEmail());
            assertThat(u.getName()).isEqualTo(user.getName());
        });
    }

    @Test
    void willReturnEmptyWhenSelectUserById(){
        // Given
        int id = 0;

        // When
        var actual = underTest.selectUserById(id);

        // Then
        assertThat(actual).isEmpty();
    }

    @Test
    void existsUserWithEmail() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        User user = new User(
                email,
                name,
                "password");
        underTest.insertUser(user);

        // When
        boolean actual = underTest.existsUserWithEmail(email);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsUserWithEmailReturnsFalseWhenDoesNotExists() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // When
        boolean actual = underTest.existsUserWithEmail(email);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsUserWithId() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        User user = new User(
                email,
                FAKER.name().fullName(),
                "password");
        underTest.insertUser(user);

        int id = underTest.selectAllUsers()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .map(User::getId)
                .findFirst()
                .orElseThrow();

        // When
        var actual = underTest.existsUserWithId(id);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsUserWithIdWillReturnFalseWhenIdNotPresent(){
        // Given
        int id = -1;

        // When
        var actual = underTest.existsUserWithId(id);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void deleteUserById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        User user = new User(
                email,
                FAKER.name().fullName(),
                "password");
        underTest.insertUser(user);

        int id = underTest.selectAllUsers()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .map(User::getId)
                .findFirst()
                .orElseThrow();

        // When
        underTest.deleteUserById(id);

        // Then
        Optional<User> actual = underTest.selectUserById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void updateUserEmail() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        User user = new User(
                email,
                FAKER.name().fullName(),
                "password");
        underTest.insertUser(user);

        int id = underTest.selectAllUsers()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .map(User::getId)
                .findFirst()
                .orElseThrow();

        var newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // When email is changed
        User update = new User();
        update.setId(id);
        update.setEmail(newEmail);

        underTest.updateUser(update);

        // Then
        Optional<User> actual = underTest.selectUserById(id);
        assertThat(actual).isPresent().hasValueSatisfying(u -> {
            assertThat(u.getId()).isEqualTo(id);
            assertThat(u.getEmail()).isEqualTo(newEmail);
            assertThat(u.getName()).isEqualTo(user.getName());
        });
    }

    @Test
    void updateUserName(){
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        User user = new User(
                email,
                FAKER.name().fullName(),
                "password");
        underTest.insertUser(user);

        int id = underTest.selectAllUsers()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .map(User::getId)
                .findFirst()
                .orElseThrow();

        var newName = "Batman";

        // When name is changed
        User update = new User();
        update.setId(id);
        update.setName(newName);

        underTest.updateUser(update);

        // Then
        Optional<User> actual = underTest.selectUserById(id);
        assertThat(actual).isPresent().hasValueSatisfying(u -> {
            assertThat(u.getId()).isEqualTo(id);
            assertThat(u.getEmail()).isEqualTo(email);
            assertThat(u.getName()).isEqualTo(newName);
        });
    }

    @Test
    void willUpdateAllPropertiesUser() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        User user = new User(
                email,
                FAKER.name().fullName(),
                "password");
        underTest.insertUser(user);

        int id = underTest.selectAllUsers()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .map(User::getId)
                .findFirst()
                .orElseThrow();

        var newName = "Batman";
        var newEmail = UUID.randomUUID().toString();

        // When name and email are changed
        User update = new User();
        update.setId(id);
        update.setName(newName);
        update.setEmail(newEmail);

        underTest.updateUser(update);

        // Then
        Optional<User> actual = underTest.selectUserById(id);
        assertThat(actual).isPresent().hasValueSatisfying(u -> {
            assertThat(u.getId()).isEqualTo(id);
            assertThat(u.getEmail()).isEqualTo(newEmail);
            assertThat(u.getName()).isEqualTo(newName);
        });
    }

    @Test
    void willNotUpdateWhenNothingToUpdate(){
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        User user = new User(
                email,
                FAKER.name().fullName(),
                "password");
        underTest.insertUser(user);

        int id = underTest.selectAllUsers()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .map(User::getId)
                .findFirst()
                .orElseThrow();

        // When
        User update = new User();
        update.setId(id);

        underTest.updateUser(update);

        // Then
        Optional<User> actual = underTest.selectUserById(id);
        assertThat(actual).isPresent().hasValueSatisfying(u -> {
            assertThat(u.getId()).isEqualTo(id);
            assertThat(u.getEmail()).isEqualTo(user.getEmail());
            assertThat(u.getName()).isEqualTo(user.getName());
        });
    }
}
