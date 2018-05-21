package com.dawhey.retailcart.controllers;

import com.dawhey.retailcart.entities.CartUser;
import com.dawhey.retailcart.entities.Product;
import com.dawhey.retailcart.entities.PurchaseAction;
import com.dawhey.retailcart.entities.ShoppingCart;
import com.dawhey.retailcart.repositories.CartUserRepository;
import com.dawhey.retailcart.repositories.ProductRepository;
import com.dawhey.retailcart.repositories.PurchaseActionRepository;
import com.dawhey.retailcart.repositories.ShoppingCartRepository;
import com.dawhey.retailcart.request.ProductScanRequest;
import com.dawhey.retailcart.response.GetProductsResponse;
import com.dawhey.retailcart.response.ProductScanResponse;
import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller
public class ShoppingController {

    @Autowired
    private CartUserRepository userRepository;

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private PurchaseActionRepository purchaseActionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    Environment env;


    @GetMapping("/products")
    @ResponseBody
    public GetProductsResponse getProducts(@RequestParam("token") String token) {
        CartUser user = userRepository.findOneByToken(token);
        if (user != null) {
            ShoppingCart cart = cartRepository.findFirstByCurrentUser(user);
            if (cart != null) {
                PurchaseAction action = purchaseActionRepository.findFirstByCart(cart);
                return new GetProductsResponse("success", action.getProducts());
            }
        }
        return new GetProductsResponse("failure", null);
    }

    @PostMapping("/scan")
    @ResponseBody
    public ProductScanResponse scanProduct(@RequestBody ProductScanRequest request) {
        Optional<ShoppingCart> cart = cartRepository.findById(Long.valueOf(request.getCartId()));
        Optional<Product> product = productRepository.findById(Long.valueOf(request.getProductId()));
        if (cart.isPresent() && product.isPresent()) {
            PurchaseAction purchaseAction = purchaseActionRepository.findFirstByCart(cart.get());
            if (purchaseAction != null) {
                Product p = product.get();
                if (p.getPurchaseAction() != null) {
                    p.setPurchaseAction(null);
                    productRepository.save(p);
                    pushRemovedMessage(p);
                    return new ProductScanResponse("success", "removed");
                }
                p.setPurchaseAction(purchaseAction);
                productRepository.save(p);
                pushAddedMessage(p);
                return new ProductScanResponse("success", "added");
            }
        }
        return new ProductScanResponse("failure", "");
    }

    private void pushRemovedMessage(Product p) {
        Pusher pusher = new Pusher(env.getProperty("pusher.app_id"),env.getProperty("pusher.key"), env.getProperty("pusher.secret"));
        pusher.setCluster(env.getProperty("pusher.cluster"));
        pusher.setEncrypted(true);
        p.setPurchaseAction(null);
        pusher.trigger("shopping-cart-channel", "removed-event", p);
    }

    private void pushAddedMessage(Product p) {
        Pusher pusher = new Pusher(env.getProperty("pusher.app_id"),env.getProperty("pusher.key"), env.getProperty("pusher.secret"));
        pusher.setCluster(env.getProperty("pusher.cluster"));
        pusher.setEncrypted(true);
        p.setPurchaseAction(null);
        pusher.trigger("shopping-cart-channel", "added-event", p);
    }
}
