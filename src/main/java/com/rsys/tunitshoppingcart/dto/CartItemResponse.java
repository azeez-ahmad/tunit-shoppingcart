package com.rsys.tunitshoppingcart.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rsys.tunitshoppingcart.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {

    private Product product;
    private Long quantity;

    @JsonIgnore
    private Long cartId;
}
