package io.borlandfcsd.cinemasystem.controller;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.User;
import io.borlandfcsd.cinemasystem.service.SecurityService;
import io.borlandfcsd.cinemasystem.service.impl.userServices.UserService;
import io.borlandfcsd.cinemasystem.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;


    @Autowired
    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

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
    public String signUp(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, HttpServletRequest request) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "signInUp";
        }

        userService.signUp(userForm);

        securityService.autoLogin(userForm.getEmail(), userForm.getPassword());

        String referer = request.getHeader("Referer");
        return "redirect:/" + referer;
    }


    @PostMapping(value = "/signIn")
    public String signIn(Model model, String error, String signOut, HttpServletRequest request) {
        if (error != null) {
            model.addAttribute("error", "Model or password is incorrect");
        }
        if (signOut != null) {
            model.addAttribute("message", "Logged out successfully");
        }
        String referer = request.getHeader("Referer");
        return "redirect:/" + referer;
    }

    @GetMapping(value = "/profile")
    public String getProfile(Model model, Principal principal) {
        User user = userService.getByEmail(principal.getName());
        model.addAttribute("userInfo", user);
        model.addAttribute("edit", false);
        return "/user/profile";
    }

    @GetMapping(value = "/profile/editProfile")
    public String editProfilePage(Model model, Principal principal) {
        User user = userService.getByEmail(principal.getName());
        user.setPassword("");
        if(!model.containsAttribute("userInfo")) {
            model.addAttribute("userInfo", user);
        }
        model.addAttribute("edit", true);

        return "/user/profile";
    }

    @PostMapping(value = "/profile/editProfile/save")
    public String editProfile(@ModelAttribute final User userInfo, final BindingResult bindingResult, Model model, RedirectAttributes redirect) {

        userValidator.validetteProfile(userInfo, bindingResult);
        if (bindingResult.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.userInfo", bindingResult);
            redirect.addFlashAttribute("userInfo", userInfo);
            return "redirect:/profile/editProfile";
        }

        userService.updateUser(userInfo);
        model.addAttribute("edit", false);
        return "user/profile";
    }

}
