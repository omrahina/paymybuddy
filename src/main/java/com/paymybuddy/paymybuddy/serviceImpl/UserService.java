package com.paymybuddy.paymybuddy.serviceImpl;

import com.paymybuddy.paymybuddy.Repository.UserRepository;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.IUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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

}
