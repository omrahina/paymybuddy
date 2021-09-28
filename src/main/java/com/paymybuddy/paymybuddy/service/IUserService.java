package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.common.ExistingUserException;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.model.User;

import java.util.List;

public interface IUserService {

    /**
     * Find a specific user in the application
     * @param email a unique identifier
     * @return the user if exists or null
     */
    User findUser(String email);

    /**
     * This method creates an account for a new user
     * @param userDTO
     * @return the newly created user
     * @throws ExistingUserException
     */
    User createAccount(UserDTO userDTO) throws ExistingUserException;

    /**
     * Update user information
     * @param userDTO
     * @return updated user if exists
     */
    User updateUser(UserDTO userDTO);

    /**
     * Create a connection between two accounts
     * @param user the owner
     * @param buddy the buddy
     * @return updated user if exists
     */
    User addConnection(User user, User buddy);

    /**
     * Get emails representing all the accounts a user is connected to
     * @param user
     * @return list of emails
     */
    List<String> getConnections(User user);

}
