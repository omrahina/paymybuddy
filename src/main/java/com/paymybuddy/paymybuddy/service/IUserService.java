package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.User;

public interface IUserService {

    User findUser(String email);

}