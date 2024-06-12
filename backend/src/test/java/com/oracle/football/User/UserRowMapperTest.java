package com.oracle.football.User;

import com.oracle.football.model.User;
import com.oracle.football.repository.UserRowMapper;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        // Given
        UserRowMapper userRowMapper = new UserRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Agent007");
        when(resultSet.getString("email")).thenReturn("agent007@gmail.com");
        when(resultSet.getString("password")).thenReturn("password");

        // When
        User actual = userRowMapper.mapRow(resultSet, 1);

        // Then
        User expected = new User(
                1,
                "agent007@gmail.com",
                "Agent007",
                "password");
        assertThat(actual).isEqualTo(expected);
    }
}
