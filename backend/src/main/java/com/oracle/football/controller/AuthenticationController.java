package com.oracle.football.controller;

import com.oracle.football.dto.AuthenticationRequest;
import com.oracle.football.dto.AuthenticationResponse;
import com.oracle.football.service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
    AuthenticationResponse response = authenticationService.login(request);
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, response.token())
        .body(response);
  }
}
