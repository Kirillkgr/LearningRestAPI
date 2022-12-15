package com.kirillzhdanov.learningrestapi.rest;

import com.kirillzhdanov.learningrestapi.models.Users;
import com.kirillzhdanov.learningrestapi.repository.UserRepository;
import com.kirillzhdanov.learningrestapi.security.JwtTokenRepository;
import com.kirillzhdanov.learningrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService service;

    @Autowired
    JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;

    public AuthController(UserService service,
                          UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Users getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        User user = (principal instanceof User) ? (User) principal : null;
        service.setLogin(user.getUsername()!=null?user.getUsername():"");
        return Objects.nonNull(user) ? this.service.getByLogin(user.getUsername()) : null;
    }

}
