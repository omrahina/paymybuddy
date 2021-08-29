package com.paymybuddy.paymybuddy.serviceImpl;

import com.paymybuddy.paymybuddy.Common.ExistingUserException;
import com.paymybuddy.paymybuddy.Dto.UserDTO;
import com.paymybuddy.paymybuddy.Repository.UserRepository;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.IUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private boolean emailExists(String email){
        return userRepository.findUserByEmail(email) != null;
    }
}
