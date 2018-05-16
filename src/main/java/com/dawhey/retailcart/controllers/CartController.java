package com.dawhey.retailcart.controllers;

import com.dawhey.retailcart.entities.CartUser;
import com.dawhey.retailcart.entities.PurchaseAction;
import com.dawhey.retailcart.entities.ShoppingCart;
import com.dawhey.retailcart.repositories.CartUserRepository;
import com.dawhey.retailcart.repositories.PurchaseActionRepository;
import com.dawhey.retailcart.repositories.ShoppingCartRepository;
import com.dawhey.retailcart.request.UserCartBindingRequest;
import com.dawhey.retailcart.request.UserCartUnbindingRequest;
import com.dawhey.retailcart.response.HasUserCartResponse;
import com.dawhey.retailcart.response.StatusResponse;
import com.dawhey.retailcart.response.UserCartBindingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    @Autowired
    private CartUserRepository userRepository;

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private PurchaseActionRepository purchaseActionRepository;


    @PostMapping("/bind")
    @ResponseBody
    public UserCartBindingResponse bindUserToCart(@RequestBody UserCartBindingRequest request) {
        if (request.getCartId() != null && request.getToken() != null) {
            CartUser user = userRepository.findOneByToken(request.getToken());
            ShoppingCart cart = cartRepository.findFirstById(Long.valueOf(request.getCartId()));
            if (user != null && cart != null) {
                cart.setCurrentUser(user);
                cartRepository.save(cart);
                PurchaseAction purchaseAction = new PurchaseAction();
                purchaseAction.setCart(cart);
                purchaseActionRepository.save(purchaseAction);
                return new UserCartBindingResponse("success");
            }
        }
        return new UserCartBindingResponse("failure");
    }

    @PostMapping("/unbind")
    @ResponseBody
    public StatusResponse unbindUserFromCart(@RequestBody UserCartUnbindingRequest request) {
        CartUser user = userRepository.findOneByToken(request.getToken());
        if (user != null) {
            ShoppingCart cart = cartRepository.findFirstByCurrentUser(user);
            if (cart != null) {
                cart.setCurrentUser(null);
                cartRepository.save(cart);
                return new StatusResponse("success");
            }
        }
        return new StatusResponse("failure");

    }


    @GetMapping("/has_cart")
    @ResponseBody
    public HasUserCartResponse hasUserCart(@RequestParam("token") String token) {
        CartUser user = userRepository.findOneByToken(token);
        if (user != null) {
            ShoppingCart cart = cartRepository.findFirstByCurrentUser(user);
            return cart != null ? new HasUserCartResponse("success", true, cart.getId()) : new HasUserCartResponse("success", false, -1);
        }
        return new HasUserCartResponse("failure", false, -1);
    }

}
