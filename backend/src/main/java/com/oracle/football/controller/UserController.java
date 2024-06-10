package com.oracle.football.controller;

import com.oracle.football.dto.UserDTO;
import com.oracle.football.dto.UserRegistrationRequest;
import com.oracle.football.dto.UserUpdateRequest;
import com.oracle.football.jwt.JwtUtil;
import com.oracle.football.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{userId}")
    public UserDTO getUserById(@PathVariable("userId") Integer userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        userService.addUser(request);
        String jwtToken = jwtUtil.issueToken(request.email(),"ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUserById(userId);
    }

    @PutMapping("{userId}")
    public void updateUser(@PathVariable("userId") Integer userId,
                           @RequestBody UserUpdateRequest request) {
        userService.updateUser(userId, request);
    }
}
