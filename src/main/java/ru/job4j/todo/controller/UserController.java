package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    
    private UserService userService;

    @GetMapping("/register")
    String getRegistrationPage(Model model) {
        model.addAttribute("timeZones", userService.getAllTimeZones());
        return "users/register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        var savedUser = userService.save(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "errors/409";
        }
        return "redirect:/";        
    }
    
    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user,
            Model model, HttpSession session) {
        
        var userOptional = userService.findByLoginAndPassword(
                user.getLogin(), user.getPassword());
        
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Логин или пароль введены неверно");
            return "users/login";
        }
        session.setAttribute("user", userOptional.get());
        return "redirect:/";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
    
}
