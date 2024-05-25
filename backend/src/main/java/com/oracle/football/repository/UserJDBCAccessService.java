package com.oracle.football.repository;

import com.oracle.football.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class UserJDBCAccessService implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public UserJDBCAccessService(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<User> selectAllUsers() {
        var sql = """
                SELECT id, email, name, password
                FROM users
                LIMIT 1000
                """;
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public Optional<User> selectUserById(Integer userId) {
        var sql = """
                SELECT id, email, name, password
                FROM users
                WHERE id = ?
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, userRowMapper, userId));
    }

    @Override
    public Optional<User> selectUserByEmail(String email) {
        var sql = """
                SELECT id, email, name, password
                FROM users
                WHERE email = ?
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, userRowMapper, email));
    }

    @Override
    public void insertUser(User user){
        var sql = """
                INSERT INTO users (email, name, password)
                VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(sql, user.getEmail(), user.getName(), user.getPassword());
    }

    @Override
    public boolean existsUserWithEmail(String email) {
        var sql = """
                SELECT count(id)
                FROM users
                WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsUserWithId(Integer userId) {
        var sql = """
                SELECT count(id)
                FROM users
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }

    @Override
    public void deleteUserById(Integer userId) {
        var sql = """
                DELETE FROM users
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public void updateUser(User updatedUser) {
        if (updatedUser.getName() != null) {
            String sql = "UPDATE users SET name = ? WHERE id = ?";
            jdbcTemplate.update(sql, updatedUser.getName(), updatedUser.getId());
        }
        if (updatedUser.getEmail() != null) {
            String sql = "UPDATE users SET email = ? WHERE id = ?";
            jdbcTemplate.update(sql, updatedUser.getEmail(), updatedUser.getId());
        }
    }
}
