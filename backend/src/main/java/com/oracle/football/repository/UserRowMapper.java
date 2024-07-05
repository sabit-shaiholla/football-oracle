package com.oracle.football.repository;

import com.oracle.football.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

  @Override
  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new User(
        rs.getInt("id"),
        rs.getString("email"),
        rs.getString("name"),
        rs.getString("password")
    );
  }
}
