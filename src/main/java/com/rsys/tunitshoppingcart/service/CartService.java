package com.rsys.tunitshoppingcart.service;

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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartItemsRepository cartItemsRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    public CartResponse addProductToCart( Long cartId,  CartDto cart) {
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

    public CartResponse getCartByCartId(Long cartId) {
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

    public void deleteProductFromCart( Long cartId, Long productId) {
        Optional<CartItems> byCartIdAndProductId = cartItemsRepository.findByCartIdAndProductId(cartId, productId);
        if (byCartIdAndProductId.isPresent()) {
            cartItemsRepository.delete(byCartIdAndProductId.get());
        } else {
            throw new CartException("Product not found in cart");
        }
    }


}
