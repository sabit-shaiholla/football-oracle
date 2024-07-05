package com.oracle.football.service;

import com.oracle.football.dto.UserDTO;
import com.oracle.football.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

  @Override
  public UserDTO apply(User user) {
    return new UserDTO(
        user.getId(),
        user.getEmail(),
        user.getName(),
        user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()),
        user.getUsername()
    );
  }
}
