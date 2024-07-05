package com.oracle.football.repository;

import com.oracle.football.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class UserJDBCAccessService implements UserDao {

  private static final Logger logger = LoggerFactory.getLogger(UserJDBCAccessService.class);

  private final JdbcTemplate jdbcTemplate;
  private final UserRowMapper userRowMapper;

  public UserJDBCAccessService(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.userRowMapper = userRowMapper;
  }

  @Override
  public List<User> selectAllUsers() {
    var sql = """
        SELECT id, name, email, password
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
    return jdbcTemplate.query(sql, userRowMapper, userId)
        .stream()
        .findFirst();
  }

  @Override
  public Optional<User> selectUserByEmail(String email) {
    var sql = """
        SELECT id, email, name, password
        FROM users
        WHERE email = ?
        """;
    return jdbcTemplate.query(sql, userRowMapper, email)
        .stream()
        .findFirst();
  }

  @Override
  public void insertUser(User user) {
    var sql = """
        INSERT INTO users (name, email, password)
        VALUES (?, ?, ?)
        """;
    int result = jdbcTemplate.update(
        sql,
        user.getName(),
        user.getEmail(),
        user.getPassword());
    logger.debug("insertUser result: {}", result);
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
    int result = jdbcTemplate.update(sql, userId);
    logger.debug("deleteUserById result: {}", result);
  }

  @Override
  public void updateUser(User updatedUser) {
    if (updatedUser.getName() != null) {
      String sql = "UPDATE users SET name = ? WHERE id = ?";
      int result = jdbcTemplate.update(
          sql,
          updatedUser.getName(),
          updatedUser.getId());
      logger.debug("updateUser name result: {}", result);
    }
    if (updatedUser.getEmail() != null) {
      String sql = "UPDATE users SET email = ? WHERE id = ?";
      int result = jdbcTemplate.update(
          sql,
          updatedUser.getEmail(),
          updatedUser.getId());
      logger.debug("updateUser username result: {}", result);
    }
  }
}
