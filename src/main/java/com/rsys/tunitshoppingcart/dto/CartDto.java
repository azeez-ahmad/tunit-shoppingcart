package com.rsys.tunitshoppingcart.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CartDto {


    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("quantity")
    private Long quantity;
}
