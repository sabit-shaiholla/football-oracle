package com.oracle.football.service;

import com.oracle.football.dto.UserDTO;
import com.oracle.football.dto.UserRegistrationRequest;
import com.oracle.football.dto.UserUpdateRequest;
import com.oracle.football.exception.DuplicateResourceException;
import com.oracle.football.exception.RequestValidationException;
import com.oracle.football.exception.ResourceNotFoundException;
import com.oracle.football.model.User;
import com.oracle.football.repository.UserDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(@Qualifier("jdbc") UserDao userDao,
                       PasswordEncoder passwordEncoder,
                       UserDTOMapper userDTOMapper) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.userDTOMapper = userDTOMapper;
    }

    public List<UserDTO> getAllUsers() {
        return userDao.selectAllUsers()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer userId) {
        return userDao.selectUserById(userId)
                .map(userDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
    }

    public void addUser(UserRegistrationRequest userRegistrationRequest) {
        String email = userRegistrationRequest.email();
        if (userDao.existsUserWithEmail(email)) {
            throw new DuplicateResourceException("User with username " + email + " already exists");
        }

        User user = new User(
                userRegistrationRequest.email(),
                userRegistrationRequest.name(),
                passwordEncoder.encode(userRegistrationRequest.password())
        );

        userDao.insertUser(user);
    }

    public void deleteUserById(Integer userId) {
        if (!userDao.existsUserWithId(userId)) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }
        userDao.deleteUserById(userId);
    }

    public void updateUser(Integer userId,
                           UserUpdateRequest userUpdateRequest) {
        User user = userDao.selectUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        boolean changes = false;

        if (userUpdateRequest.email() != null && !userUpdateRequest.email().equals(user.getEmail())) {
            if (userDao.existsUserWithEmail(userUpdateRequest.email())) {
                throw new DuplicateResourceException("User with username " + userUpdateRequest.email() + " already exists");
            }
            user.setEmail(userUpdateRequest.email());
            changes = true;
        }
        if (userUpdateRequest.name() != null && !userUpdateRequest.name().equals(user.getName())) {
            user.setName(userUpdateRequest.name());
            changes = true;
        }

        if(!changes){
            throw new RequestValidationException("No changes detected");
        }

        userDao.updateUser(user);
    }
}
