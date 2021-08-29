package com.paymybuddy.paymybuddy.Controller;

import com.paymybuddy.paymybuddy.serviceImpl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class UserController {

    private final UserService userService;

    public static final String REGISTRATION = "registration";
    public static final String HOME = "home";
    public static final String PROFILE = "profile";
    public static final String LOGIN = "login";
    public static final String TRANSFER = "transfer";

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(name = "error", required = false) String error, Model model){
        model.addAttribute("error", error != null);
        return LOGIN;
    }

    @GetMapping("/logout")
    public String logout(Model model){
        return LOGIN;
    }

}
