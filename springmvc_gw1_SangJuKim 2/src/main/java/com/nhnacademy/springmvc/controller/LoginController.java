package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.*;

@Controller
public class LoginController {
    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("login") != null) {
            session.setAttribute("id", session);
            return "thymeleaf/studentRegister";
        } else {
            return "thymeleaf/loginForm";
        }
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("id") String id,
                          @RequestParam("pwd") String pwd,
                          HttpServletRequest request
    ) {
        HttpSession session = request.getSession(true);
        if (userRepository.matches(id, pwd)) {
            session.setAttribute("login", id);
            return "thymeleaf/studentRegister";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    private String expireCookie(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
