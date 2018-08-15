package io.borlandfcsd.cinemasystem.controller;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.User;
import io.borlandfcsd.cinemasystem.service.SecurityService;
import io.borlandfcsd.cinemasystem.service.impl.userServices.UserService;
import io.borlandfcsd.cinemasystem.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;

    @RequestMapping(value = "/signPage")
    public String getSignUpPage(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        model.addAttribute("userForm", new User());
        return "signInUp";
    }

    @PostMapping(value = "/signUp")
    public String signUp(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "signInUp";
        }

        userService.signUp(userForm);

        securityService.autoLogin(userForm.getEmail(), userForm.getPassword());

        return "redirect:/";
    }


    @PostMapping(value = "/signIn")
    public String signIn(Model model, String error, String signOut) {
        if (error != null) {
            model.addAttribute("error", "Model or password is incorrect");
        }
        if (signOut != null) {
            model.addAttribute("message", "Logged out successfully");
        }

        return "redirect:/";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Autowired
    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }
}
