package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.exception.UserNotFoundException;
import com.xhaven.xhavenserver.model.entity.User;
import com.xhaven.xhavenserver.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void Should_ReturnUserById_When_UserExists() {
        //given
        Long USER_ID = 1L;
        User user = User.builder()
                .id(USER_ID)
                .build();

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        //when
        User actualUser = userService.getUserById(USER_ID);

        //then
        assertEquals(USER_ID, actualUser.getId());
    }

    @Test
    void Should_ThrowException_When_UserDoesNotExist() {
        //given
        Long NON_EXISTENT_USER_ID = 99L;

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        String EXPECTED_ERROR_MESSAGE = "User with ID " + NON_EXISTENT_USER_ID + " not found";

        //when
        Executable executable = () -> userService.getUserById(NON_EXISTENT_USER_ID);

        //then
        assertThrows(UserNotFoundException.class, executable, EXPECTED_ERROR_MESSAGE);
    }

}
