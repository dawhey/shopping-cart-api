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
import com.dawhey.retailcart.request.UserCartBindingRequest;
import com.dawhey.retailcart.response.ProductScanResponse;
import com.dawhey.retailcart.response.UserCartBindingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping("/scan")
    @ResponseBody
    public ProductScanResponse scanProduct(@RequestBody ProductScanRequest request) {
        Optional<ShoppingCart> cart = cartRepository.findById(Long.valueOf(request.getCartId()));
        Optional<Product> product = productRepository.findById(Long.valueOf(request.getProductId()));
        if (cart.isPresent() && product.isPresent()) {
            PurchaseAction purchaseAction = purchaseActionRepository.findFirstByCart(cart.get());
            if (purchaseAction != null) {
                Product p = product.get();
                p.setPurchaseAction(purchaseAction);
                productRepository.save(p);
                return new ProductScanResponse("success", p.getPurchaseAction().getId());
            }
        }
        return new ProductScanResponse("failure", -1);
    }
}
