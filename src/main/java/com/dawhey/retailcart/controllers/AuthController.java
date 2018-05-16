package com.dawhey.retailcart.controllers;

import com.dawhey.retailcart.entities.CartUser;
import com.dawhey.retailcart.entities.ShoppingCart;
import com.dawhey.retailcart.repositories.ShoppingCartRepository;
import com.dawhey.retailcart.request.AuthenticationRequest;
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

    @Autowired
    ShoppingCartRepository cartRepository;

    @PostMapping("/authenticate")
    @ResponseBody
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest user) {
        CartUser cartUser = cartUserRepository.findOneByUserName(user.getUserName());
        if (cartUser != null && cartUser.getPassword().equals(user.getPassword())) {
            String token = UUID.randomUUID().toString();
            cartUser.setToken(token);
            cartUserRepository.save(cartUser);
            unbindUserFromCart(cartUser);
            return new AuthenticationResponse("success", token);
        } else {
            return new AuthenticationResponse("failure", null);
        }
    }

    private void unbindUserFromCart(CartUser user) {
        ShoppingCart cart = cartRepository.findFirstByCurrentUser(user);
        if (cart != null) {
            cart.setCurrentUser(null);
            cartRepository.save(cart);
        }
    }
}
