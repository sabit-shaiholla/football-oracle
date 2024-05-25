package com.oracle.football.repository;

import com.oracle.football.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> selectAllUsers();
    Optional<User> selectUserById(Integer id);
    void insertUser(User user);
    boolean existsUserWithEmail(String email);
    boolean existsUserWithId(Integer userId);
    void deleteUserById(Integer userId);
    void updateUser(User updatedUser);
    Optional<User> selectUserByEmail(String email);
}
