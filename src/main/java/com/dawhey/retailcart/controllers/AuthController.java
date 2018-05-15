package com.dawhey.retailcart.controllers;

import com.dawhey.retailcart.entities.CartUser;
import com.dawhey.retailcart.entities.UserEntity;
import com.dawhey.retailcart.repositories.CartUserRepository;
import com.dawhey.retailcart.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;


@Controller
public class AuthController {

    @Autowired
    CartUserRepository cartUserRepository;

    @PostMapping("/authenticate")
    @ResponseBody
    public AuthenticationResponse authenticate(@RequestBody UserEntity user) {
        CartUser cartUser = cartUserRepository.findOneByUserName(user.getUserName());
        if (cartUser != null && cartUser.getPassword().equals(user.getPassword())) {
            String token = UUID.randomUUID().toString();
            cartUser.setToken(token);
            cartUserRepository.save(cartUser);
            return new AuthenticationResponse("success", token);
        } else {
            return new AuthenticationResponse("failure", null);
        }
    }
}
