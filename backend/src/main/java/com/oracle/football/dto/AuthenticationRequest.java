package com.oracle.football.dto;

public record AuthenticationRequest(
    String username,
    String password) {

}
