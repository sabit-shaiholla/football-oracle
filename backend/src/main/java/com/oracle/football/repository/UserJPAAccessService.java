package com.oracle.football.repository;

import com.oracle.football.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class UserJPAAccessService implements UserDao {

  private final UserRepository userRepository;

  public UserJPAAccessService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> selectAllUsers() {
    Page<User> page = userRepository.findAll(Pageable.ofSize(1000));
    return page.getContent();
  }

  @Override
  public Optional<User> selectUserById(Integer userId) {
    return userRepository.findById(userId);
  }

  @Override
  public Optional<User> selectUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public void insertUser(User user) {
    userRepository.save(user);
  }

  @Override
  public boolean existsUserWithEmail(String email) {
    return userRepository.existsUserByEmail(email);
  }

  @Override
  public boolean existsUserWithId(Integer userId) {
    return userRepository.existsUserById(userId);
  }

  @Override
  public void deleteUserById(Integer userId) {
    userRepository.deleteById(userId);
  }

  @Override
  public void updateUser(User updatedUser) {
    userRepository.save(updatedUser);
  }
}
