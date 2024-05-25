package com.oracle.football.dto;

public record AuthenticationRequest(
        String email,
        String password) {
}
