package com.oracle.football.controller;

import com.oracle.football.dto.UserDTO;
import com.oracle.football.dto.UserRegistrationRequest;
import com.oracle.football.dto.UserUpdateRequest;
import com.oracle.football.jwt.JwtUtil;
import com.oracle.football.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling user-related requests.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/users")
public class UserController {

  private final UserService userService;
  private final JwtUtil jwtUtil;

  /**
   * Constructs a {@code UserController} with the specified user service and JWT utility.
   *
   * @param userService the user service to use
   * @param jwtUtil the JWT utility to use
   */
  public UserController(UserService userService, JwtUtil jwtUtil) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
  }

  /**
   * Retrieves all users.
   *
   * @return a list of all users
   */
  @Operation(summary = "Get all users", description = "Retrieves a list of all users.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  public List<UserDTO> getUsers() {
    return userService.getAllUsers();
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param userId the ID of the user to retrieve
   * @return the user with the specified ID
   */
  @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "404", description = "User not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("{userId}")
  public UserDTO getUserById(@PathVariable("userId") Integer userId) {
    return userService.getUserById(userId);
  }

  /**
   * Registers a new user.
   *
   * @param request the user registration request containing user details
   * @return a response entity with a JWT token in the authorization header
   */
  @Operation(summary = "Register a new user", description = "Registers a new user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User registered successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
    userService.addUser(request);
    String jwtToken = jwtUtil.issueToken(request.email(), "ROLE_USER");
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        .build();
  }

  /**
   * Deletes a user by their ID.
   *
   * @param userId the ID of the user to delete
   */
  @Operation(summary = "Delete a user", description = "Deletes a user by their ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "User deleted successfully"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "404", description = "User not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("{userId}")
  public void deleteUser(@PathVariable("userId") Integer userId) {
    userService.deleteUserById(userId);
  }

  /**
   * Updates a user by their ID.
   *
   * @param userId the ID of the user to update
   * @param request the user update request containing updated user details
   */
  @Operation(summary = "Update a user", description = "Updates a user by their ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User updated successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "404", description = "User not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("{userId}")
  public void updateUser(@PathVariable("userId") Integer userId,
      @RequestBody UserUpdateRequest request) {
    userService.updateUser(userId, request);
  }
}
