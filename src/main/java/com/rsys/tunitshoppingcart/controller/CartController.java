package com.rsys.tunitshoppingcart.controller;


import com.rsys.tunitshoppingcart.dto.CartDto;
import com.rsys.tunitshoppingcart.dto.CartResponse;
import com.rsys.tunitshoppingcart.service.CartService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping("/{cartId}/add")
    public CartResponse addProductToCart(@PathVariable Long cartId, @RequestBody CartDto cart) {
        return cartService.addProductToCart(cartId, cart);
    }

    @GetMapping("/get")
    public List<CartResponse> getCart() {
        return cartService.getCart();
    }

    @GetMapping("/get/{cartId}")
    public CartResponse getCart(@PathVariable Long cartId) {
        return cartService.getCartByCartId(cartId);
    }


    @DeleteMapping("/delete/{cartId}/{productId}")
    public void deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        cartService.deleteProductFromCart(cartId, productId);
    }


}
