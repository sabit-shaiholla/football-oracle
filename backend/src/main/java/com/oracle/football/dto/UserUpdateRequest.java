package com.oracle.football.dto;

public record UserUpdateRequest(
        String name,
        String email
) {
}
