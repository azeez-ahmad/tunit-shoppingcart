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


    /**
     * This method is used to add product to cart for a given cartId, if cartId is not present then it will create a new cart
     * @param cartId cart id
     * @param cart  cart
     * @return cart response
     * @author rsys azeez
     *
     */
    @PostMapping("/{cartId}/add")
    public CartResponse addProductToCart(@PathVariable Long cartId, @RequestBody CartDto cart) {
        return cartService.addProductToCart(cartId, cart);
    }

    /**
     * This method is used to list all the products in all the carts
     * @return cart response
     * @author rsys azeez
     *
     */
    @GetMapping("/get")
    public List<CartResponse> getCart() {
        return cartService.getCart();
    }


    /**
     * This method returns the cart information for a given cartId.
     * It will return the cart information with the product detail with associated quantity
     * @param cartId cart id
     * @return cart response
     * @author rsys azeez
     *
     */
    @GetMapping("/get/{cartId}")
    public CartResponse getCart(@PathVariable Long cartId) {
        return cartService.getCartByCartId(cartId);
    }


    /**
     * This method is used to delete a product from a cart
     * @param cartId cart id
     * @param productId product id
     * @return cart response
     * @author rsys azeez
     *
     */
    @DeleteMapping("/delete/{cartId}/{productId}")
    public void deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        cartService.deleteProductFromCart(cartId, productId);
    }


}
