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
import com.dawhey.retailcart.response.GetProductsResponse;
import com.dawhey.retailcart.response.HasUserCartResponse;
import com.dawhey.retailcart.response.ProductScanResponse;
import com.dawhey.retailcart.response.UserCartBindingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

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
                p.setPurchaseAction(purchaseAction);
                productRepository.save(p);
                return new ProductScanResponse("success", p.getPurchaseAction().getId());
            }
        }
        return new ProductScanResponse("failure", -1);
    }
}
