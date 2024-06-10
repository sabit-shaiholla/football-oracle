package com.oracle.football.dto;

public record AuthenticationResponse(
        String token,
        UserDTO userDTO) {
}
