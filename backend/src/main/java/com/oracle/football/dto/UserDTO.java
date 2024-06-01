package com.oracle.football.dto;

import java.util.List;
public record UserDTO (
    Long id,
    String name,
    String email,
    List<String> roles)
{}

