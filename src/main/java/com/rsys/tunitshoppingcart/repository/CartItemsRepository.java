package com.rsys.tunitshoppingcart.repository;

import com.rsys.tunitshoppingcart.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

    Optional<CartItems> findByCartIdAndProductId(Long cartId, Long productId);
    List<CartItems> findByCartId(Long cartId);
}
