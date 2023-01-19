package com.rsys.tunitshoppingcart.controller;


import com.rsys.tunitshoppingcart.dto.CartDto;
import com.rsys.tunitshoppingcart.dto.CartItemResponse;
import com.rsys.tunitshoppingcart.dto.CartResponse;
import com.rsys.tunitshoppingcart.entity.Cart;
import com.rsys.tunitshoppingcart.entity.CartItems;
import com.rsys.tunitshoppingcart.entity.Product;
import com.rsys.tunitshoppingcart.exception.CartException;
import com.rsys.tunitshoppingcart.repository.CartItemsRepository;
import com.rsys.tunitshoppingcart.repository.CartRepository;
import com.rsys.tunitshoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;

    @Autowired
    public CartController(CartRepository cartRepository,
                          ProductRepository productRepository, CartItemsRepository cartItemsRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    @PostMapping("/{cartId}/add")
    public CartResponse addProductToCart(@PathVariable Long cartId, @RequestBody CartDto cart) {
        Optional<Product> optionalProduct = productRepository.findById(cart.getProductId());
        if (optionalProduct.isEmpty()) {
            throw new CartException("Product not found");
        }
        Long currentCartId = cartId;
        Optional<Cart> existingCart = cartRepository.findById(cartId);
        if (existingCart.isPresent()) {
            Optional<CartItems> byCartIdAndProductId = cartItemsRepository.findByCartIdAndProductId(cartId, cart.getProductId());
            if (byCartIdAndProductId.isPresent()) {
                CartItems cartItems = byCartIdAndProductId.get();
                cartItems.setQuantity(cartItems.getQuantity() + cart.getQuantity());
                cartItemsRepository.save(cartItems);
            } else {
                CartItems cartItems = new CartItems();
                cartItems.setCartId(cartId);
                cartItems.setProductId(cart.getProductId());
                cartItems.setQuantity(cart.getQuantity());
                cartItemsRepository.save(cartItems);
            }

        } else {
            Cart save = cartRepository.save(Cart.builder().build());
            currentCartId = save.getId();
            CartItems cartItems = new CartItems();
            cartItems.setCartId(save.getId());
            cartItems.setProductId(cart.getProductId());
            cartItems.setQuantity(cart.getQuantity());
            cartItemsRepository.save(cartItems);

        }
        Map<Long, List<CartItems>> cartItemsByCartId = cartItemsRepository.findByCartId(currentCartId)
                .stream()
                .collect(Collectors.groupingBy(CartItems::getCartId));
        List<CartResponse> cartResponses = new ArrayList<>();
        cartItemsByCartId.forEach((key, cartItems) -> {
            List<CartItemResponse> cartItemResponse = cartItems.stream().map(cartItem -> {
                Product product = productRepository.findById(cartItem.getProductId()).get();

                return CartItemResponse.builder().product(product).cartId(key).quantity(cartItem.getQuantity()).build();
            }).toList();

            CartResponse cartResponse = new CartResponse();
            cartResponse.setCartId(key);
            cartResponse.setCartItemResponses(new ArrayList<>());
            cartResponse.getCartItemResponses().addAll(cartItemResponse);
            cartResponses.add(cartResponse);

        });
        return cartResponses.get(0);


    }

    @GetMapping("/get")
    public List<CartResponse> getCart() {
        Map<Long, List<CartItems>> cartItemsByCartId = cartItemsRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(CartItems::getCartId));
        List<CartResponse> cartResponses = new ArrayList<>();
        cartItemsByCartId.forEach((cartId, cartItems) -> {
            List<CartItemResponse> cartItemResponse = cartItems.stream().map(cartItem -> {
                Product product = productRepository.findById(cartItem.getProductId()).get();

                return CartItemResponse.builder().product(product).cartId(cartId).quantity(cartItem.getQuantity()).build();
            }).toList();

            CartResponse cartResponse = new CartResponse();
            cartResponse.setCartId(cartId);
            cartResponse.setCartItemResponses(new ArrayList<>());
            cartResponse.getCartItemResponses().addAll(cartItemResponse);
            cartResponses.add(cartResponse);

        });
        return cartResponses;
    }

    @GetMapping("/get/{cartId}")
    public CartResponse getCart(@PathVariable Long cartId) {
        Map<Long, List<CartItems>> cartItemsByCartId = cartItemsRepository.findByCartId(cartId)
                .stream()
                .collect(Collectors.groupingBy(CartItems::getCartId));
        List<CartResponse> cartResponses = new ArrayList<>();
        cartItemsByCartId.forEach((key, cartItems) -> {
            List<CartItemResponse> cartItemResponse = cartItems.stream().map(cartItem -> {
                Product product = productRepository.findById(cartItem.getProductId()).get();

                return CartItemResponse.builder().product(product).cartId(key).quantity(cartItem.getQuantity()).build();
            }).toList();

            CartResponse cartResponse = new CartResponse();
            cartResponse.setCartId(key);
            cartResponse.setCartItemResponses(new ArrayList<>());
            cartResponse.getCartItemResponses().addAll(cartItemResponse);
            cartResponses.add(cartResponse);

        });
        return cartResponses.get(0);
    }


    @DeleteMapping("/delete/{cartId}/{productId}")
    public void deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        Optional<CartItems> byCartIdAndProductId = cartItemsRepository.findByCartIdAndProductId(cartId, productId);
        if (byCartIdAndProductId.isPresent()) {
            cartItemsRepository.delete(byCartIdAndProductId.get());
        } else {
            throw new CartException("Product not found in cart");
        }
    }


}
