package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.Repository.UserRepository;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.serviceImpl.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void should_find_a_user(){
        when(userRepository.findUserByEmail(anyString())).thenReturn(new User("user1@gmail.com", "password"));
        User userFound = userService.findUser("user1@gmail.com");

        assertThat(userFound.getEmail()).isEqualTo("user1@gmail.com");
    }

    @Test
    public void should_not_find_a_user(){
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);
        User userFound = userService.findUser("user1@gmail.com");

        assertThat(userFound).isNull();
    }
}
