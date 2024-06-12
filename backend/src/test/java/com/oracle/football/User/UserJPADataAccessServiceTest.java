package com.oracle.football.User;

import com.oracle.football.model.User;
import com.oracle.football.repository.UserJPAAccessService;
import com.oracle.football.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserJPADataAccessServiceTest {

    private UserJPAAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new UserJPAAccessService(userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllUsers() {
        Page<User> page = mock(Page.class);
        List<User> users = List.of(new User());
        when(page.getContent()).thenReturn(users);
        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        // When
        List<User> expected = underTest.selectAllUsers();

        // Then
        assertThat(expected).isEqualTo(users);
        ArgumentCaptor<Pageable> pageArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(userRepository).findAll(pageArgumentCaptor.capture());
        assertThat(pageArgumentCaptor.getValue()).isEqualTo(Pageable.ofSize(1000));
    }

    @Test
    void selectUserById() {
        // Given
        int id = 1;

        // When
        underTest.selectUserById(id);

        // Then
        verify(userRepository).findById(id);
    }

    @Test
    void insertUser() {
        // Given
        User user = new User("tonystark@gmail.com", "Tony", "password");

        // When
        underTest.insertUser(user);

        // Then
        verify(userRepository).save(user);
    }

    @Test
    void existsUserWithEmail() {
        // Given
        String email = "test1@gmail.com";

        // When
        underTest.existsUserWithEmail(email);

        // Then
        verify(userRepository).existsUserByEmail(email);
    }

    @Test
    void existsUserWithId() {
        // Given
        int id = 1;

        // When
        underTest.existsUserWithId(id);

        // Then
        verify(userRepository).existsUserById(id);
    }

    @Test
    void deleteUserById() {
        // Given
        int id = 1;

        // When
        underTest.deleteUserById(id);

        // Then
        verify(userRepository).deleteById(id);
    }

    @Test
    void updateUser() {
        // Given
        User user = new User(
                1,
                "agent007@gmail.com",
                "Agent007",
                "password");

        // When
        underTest.updateUser(user);

        // Then
        verify(userRepository).save(user);
    }
}
