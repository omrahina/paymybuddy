package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.common.ExistingUserException;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.serviceImpl.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    public void should_create_a_user(){
        User user = new User("user1@gmail.com", "password");
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User newUser = userService.createAccount(new UserDTO("user1@gmail.com", "password"));

        assertThat(newUser.getEmail()).isEqualTo("user1@gmail.com");
    }

    @Test
    public void should_throw_ExistingUserException(){
        when(userRepository.findUserByEmail(anyString())).thenReturn(new User("user1@gmail.com", "password"));

        assertThatExceptionOfType(ExistingUserException.class).isThrownBy(()
                -> userService.createAccount(new UserDTO("user1@gmail.com", "password")))
                .withMessage("An account with the email address: user1@gmail.com already exists");

    }

    @Test
    public void should_update_a_user(){
        User userToUpdate = new User("user1@gmail.com", "password");
        UserDTO user = new UserDTO("user1@gmail.com", "password");
        user.setFirstName("Tati");
        userToUpdate.setFirstName(user.getFirstName());
        when(userRepository.findUserByEmail(anyString())).thenReturn(userToUpdate);
        when(userRepository.save(any(User.class))).thenReturn(userToUpdate);

        User updatedUser = userService.updateUser(user);

        assertThat(updatedUser).isNotNull();
    }

    @Test
    public void should_not_update_a_user(){
        UserDTO user = new UserDTO("user1@gmail.com", "password");
        user.setFirstName("Tati");
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);

        User updatedUser = userService.updateUser(user);

        assertThat(updatedUser).isNull();

    }

    @Test
    public void should_add_a_connection(){
        User user = new User("user1@gmail.com", "password");
        user.setBuddies(new ArrayList<>());
        User buddy = new User("user2@gmail.com", "password");
        when(userRepository.findUserByEmail(anyString())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.addConnection(user, buddy);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getBuddies()).hasSize(1);

    }

    @Test
    public void should_not_add_a_connection(){
        User user = new User("user1@gmail.com", "password");
        User buddy = new User("user2@gmail.com", "password");
        user.setBuddies(new ArrayList<>());
        user.addBuddy(buddy);
        when(userRepository.findUserByEmail(anyString())).thenReturn(user);

        User updatedUser = userService.addConnection(user, buddy);

        assertThat(updatedUser).isNull();

    }

    @Test
    public void should_return_buddies(){
        User user = new User("user1@gmail.com", "password");
        User buddy = new User("user2@gmail.com", "password");
        user.setBuddies(new ArrayList<>());
        user.addBuddy(buddy);

        List<String> connections = userService.getConnections(user);

        assertThat(connections).isNotEmpty();
        assertThat(connections).contains("user2@gmail.com");
    }
}
