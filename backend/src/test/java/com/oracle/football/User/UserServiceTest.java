package com.oracle.football.User;

import com.oracle.football.dto.UserDTO;
import com.oracle.football.dto.UserRegistrationRequest;
import com.oracle.football.dto.UserUpdateRequest;
import com.oracle.football.exception.DuplicateResourceException;
import com.oracle.football.exception.RequestValidationException;
import com.oracle.football.exception.ResourceNotFoundException;
import com.oracle.football.model.User;
import com.oracle.football.repository.UserDao;
import com.oracle.football.service.UserDTOMapper;
import com.oracle.football.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserDao userDao;
  @Mock
  private PasswordEncoder passwordEncoder;
  private UserService underTest;
  private final UserDTOMapper userDTOMapper = new UserDTOMapper();

  @BeforeEach
  void setUp() {
    underTest = new UserService(userDao, passwordEncoder, userDTOMapper);
  }

  @Test
  void getAllUsers() {
    // When
    underTest.getAllUsers();

    // Then
    verify(userDao).selectAllUsers();
  }

  @Test
  void canGetUser() {
    // Given
    int id = 10;
    User user = new User(id, "peterparker@gmail.com", "Peter", "password");
    when(userDao.selectUserById(id)).thenReturn(Optional.of(user));

    UserDTO expected = userDTOMapper.apply(user);

    // When
    UserDTO actual = underTest.getUserById(id);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void willThrowWhenGetUserReturnEmptyOptional() {
    // Given
    int id = 10;

    when(userDao.selectUserById(id)).thenReturn(Optional.empty());

    // When
    // Then
    assertThatThrownBy(() ->
        underTest.getUserById(id))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("User with id " + id + " not found");
  }

  @Test
  void addUser() {
    // Given
    String email = "peterparker@gmail.com";

    when(userDao.existsUserWithEmail(email)).thenReturn(false);

    UserRegistrationRequest request = new UserRegistrationRequest(email, "Peter", "password");

    String passwordHash = "ksa183;!#$";

    when(passwordEncoder.encode(request.password())).thenReturn(passwordHash);

    // When
    underTest.addUser(request);

    // Then
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    verify(userDao).insertUser(userArgumentCaptor.capture());

    User capturedUser = userArgumentCaptor.getValue();

    assertThat(capturedUser.getId()).isNull();
    assertThat(capturedUser.getName()).isEqualTo(request.name());
    assertThat(capturedUser.getEmail()).isEqualTo(request.email());
    assertThat(capturedUser.getPassword()).isEqualTo(passwordHash);
  }

  @Test
  void willThrowWhenEmailExistsWhileAddingAUser() {
    // Given
    String email = "peterparker@gmail.com";

    when(userDao.existsUserWithEmail(email)).thenReturn(true);

    UserRegistrationRequest request = new UserRegistrationRequest(email, "peterparker@gmail.com",
        "password");

    // When
    assertThatThrownBy(() ->
        underTest.addUser(request))
        .isInstanceOf(DuplicateResourceException.class)
        .hasMessage("User with username " + email + " already exists");

    // Then
    verify(userDao, never()).insertUser(any());
  }

  @Test
  void deleteUserById() {
    // Given
    int id = 10;

    when(userDao.existsUserWithId(id)).thenReturn(true);

    // When
    underTest.deleteUserById(id);
    // Then
    verify(userDao).deleteUserById(id);
  }

  @Test
  void willThrowDeleteUserByIdNotExists() {
    // Given
    int id = 10;

    when(userDao.existsUserWithId(id)).thenReturn(false);

    // When
    assertThatThrownBy(() ->
        underTest.deleteUserById(id))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("User with id " + id + " not found");

    // Then
    verify(userDao, never()).deleteUserById(id);
  }

  @Test
  void canUpdateAllUsersProperties() {
    // Given
    int id = 10;
    User user = new User(id, "peterparker@gmail.com", "Peter", "password");
    when(userDao.selectUserById(id)).thenReturn(Optional.of(user));

    String newEmail = "spiderman@gmail.com";

    UserUpdateRequest updateRequest = new UserUpdateRequest("Spider-man", newEmail);

    when(userDao.existsUserWithEmail(newEmail)).thenReturn(false);

    // When
    underTest.updateUser(id, updateRequest);

    // Then
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    verify(userDao).updateUser(userArgumentCaptor.capture());
    User capturedUser = userArgumentCaptor.getValue();

    assertThat(capturedUser.getName()).isEqualTo(updateRequest.name());
    assertThat(capturedUser.getEmail()).isEqualTo(updateRequest.email());
  }

  @Test
  void canUpdateOnlyUserName() {
    // Given
    int id = 10;
    User user = new User(id, "peterparker@gmail.com", "Peter", "password");
    when(userDao.selectUserById(id)).thenReturn(Optional.of(user));

    UserUpdateRequest updateRequest = new UserUpdateRequest("Spider-man", null);

    // When
    underTest.updateUser(id, updateRequest);

    // Then
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    verify(userDao).updateUser(userArgumentCaptor.capture());
    User capturedUser = userArgumentCaptor.getValue();

    assertThat(capturedUser.getName()).isEqualTo(updateRequest.name());
    assertThat(capturedUser.getEmail()).isEqualTo(user.getEmail());
  }

  @Test
  void canUpdateOnlyUserEmail() {
    // Given
    int id = 10;
    User user = new User(id, "peterparker@gmail.com", "Peter", "password");
    when(userDao.selectUserById(id)).thenReturn(Optional.of(user));

    String newEmail = "spiderman@gmail.com";

    UserUpdateRequest updateRequest = new UserUpdateRequest(null, newEmail);

    when(userDao.existsUserWithEmail(newEmail)).thenReturn(false);

    // When
    underTest.updateUser(id, updateRequest);

    // Then
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    verify(userDao).updateUser(userArgumentCaptor.capture());
    User capturedUser = userArgumentCaptor.getValue();

    assertThat(capturedUser.getName()).isEqualTo(user.getName());
    assertThat(capturedUser.getEmail()).isEqualTo(newEmail);
  }

  @Test
  void willThrowWhenTryingToUpdateUserEmailWhenAlreadyTaken() {
    // Given
    int id = 10;
    User user = new User(id, "peterparker@gmail.com", "Peter", "password");
    when(userDao.selectUserById(id)).thenReturn(Optional.of(user));

    String newEmail = "spiderman@gmail.com";

    UserUpdateRequest updateRequest = new UserUpdateRequest(null, newEmail);

    when(userDao.existsUserWithEmail(newEmail)).thenReturn(true);

    // When
    assertThatThrownBy(() ->
        underTest.updateUser(id, updateRequest))
        .isInstanceOf(DuplicateResourceException.class)
        .hasMessage("User with username " + newEmail + " already exists");

    // Then
    verify(userDao, never()).updateUser(any());
  }

  @Test
  void willThrowWhenUserUpdateHasNoChanges() {
    // Given
    int id = 10;
    User user = new User(id, "peterparker@gmail.com", "Peter", "password");
    when(userDao.selectUserById(id)).thenReturn(Optional.of(user));

    UserUpdateRequest updateRequest = new UserUpdateRequest(user.getName(), user.getEmail());

    // When
    assertThatThrownBy(() ->
        underTest.updateUser(id, updateRequest))
        .isInstanceOf(RequestValidationException.class)
        .hasMessage("No changes detected");

    // Then
    verify(userDao, never()).updateUser(any());
  }
}
