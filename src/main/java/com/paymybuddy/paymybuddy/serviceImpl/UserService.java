package com.paymybuddy.paymybuddy.serviceImpl;

import com.paymybuddy.paymybuddy.common.ExistingUserException;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.IUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Data
@Slf4j
public class UserService implements IUserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User findUser(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user != null){
            log.info("user found");
            return user;
        }
        log.error("user not found");
        return null;
    }

    @Override
    public User createAccount(UserDTO userDTO) throws ExistingUserException{
        if (emailExists(userDTO.getEmail())) {
            log.error("Error: existing email");
            throw new ExistingUserException("An account with the email address: "+ userDTO.getEmail()
                    + " already exists");
        }
        User newUser = new User(userDTO.getEmail(), new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        log.info("user with email "+ userDTO.getEmail() + " created");

        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(UserDTO user) {
        User userToUpdate = userRepository.findUserByEmail(user.getEmail());
        if (userToUpdate != null){
//            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setFirstName(user.getFirstName() != null ? user.getFirstName() : null);
            userToUpdate.setLastName(user.getLastName() != null ? user.getLastName() : null);
            userToUpdate.setBankAccount(user.getBankAccount() != null ? user.getBankAccount() : null);
            userToUpdate = userRepository.save(userToUpdate);
            log.info("user updated");

            return userToUpdate;
        }
        log.error("Failed to update the user");
        return null;
    }

    @Override
    public User addConnection(User user, User buddy){
        User userToUpdate = userRepository.findUserByEmail(user.getEmail());
        if(!userToUpdate.getBuddies().contains(buddy)){
            userToUpdate.addBuddy(buddy);
            log.info("buddy added!");
            return userRepository.save(userToUpdate);
        }
        log.error("user and buddy already connected");
        return null;
    }

    @Override
    public List<String> getConnections(User user) {
        return user.getBuddies().stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

    /**
     * Verify if an email already exists in the application
     * @param email a string
     * @return true or false depending on whether the email exists or not
     */
    private boolean emailExists(String email){
        return userRepository.findUserByEmail(email) != null;
    }
}
