package com.oracle.football.controller;

import com.oracle.football.dto.AuthenticationRequest;
import com.oracle.football.dto.AuthenticationResponse;
import com.oracle.football.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication requests.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  /**
   * Constructs an {@code AuthenticationController} with the specified authentication service.
   *
   * @param authenticationService the authentication service to use
   */
  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  /**
   * Handles the login request and returns a response with a JWT token.
   *
   * @param request the authentication request containing user credentials
   * @return a response entity containing the authentication response with a JWT token
   */
  @Operation(summary = "Login", description = "Handles the login request and returns a response with a JWT token.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
      @ApiResponse(responseCode = "400", description = "Invalid credentials"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
    AuthenticationResponse response = authenticationService.login(request);
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, response.token())
        .body(response);
  }
}
