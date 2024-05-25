package com.oracle.football.dto;

public record UserRegistrationRequest(
        String email,
        String name,
        String password) {
}
