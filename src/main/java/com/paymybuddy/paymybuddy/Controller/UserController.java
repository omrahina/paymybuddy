package com.paymybuddy.paymybuddy.Controller;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.serviceImpl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

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

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request){
        String currentUserEmail = currentUserEmail(request);
        if (currentUserEmail != null){
            User user = userService.findUser(currentUserEmail);
            model.addAttribute("user", user);
        }
        return HOME;
    }

    @GetMapping("/contact")
    public String showContactPage(Model model, HttpServletRequest request){
        User user = userService.findUser(currentUserEmail(request));
        if (user != null){
            model.addAttribute("user", user);
        }
        return "contact";
    }

    @GetMapping(value = "/userEmail")
    @ResponseBody
    public String currentUserEmail(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal != null ? principal.getName() : null;
    }

}
