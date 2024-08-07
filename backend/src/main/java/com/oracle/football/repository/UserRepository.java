package com.oracle.football.repository;

import com.oracle.football.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  boolean existsUserByEmail(String email);

  boolean existsUserById(Integer id);
}
