package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.Dto.UserDTO;
import com.paymybuddy.paymybuddy.model.User;

import java.util.List;

public interface IUserService {

    User findUser(String email);

    User createAccount(UserDTO userDTO);

    User updateUser(UserDTO userDTO);

    User addConnection(User user, User buddy);

    List<String> getConnections(User user);

}
