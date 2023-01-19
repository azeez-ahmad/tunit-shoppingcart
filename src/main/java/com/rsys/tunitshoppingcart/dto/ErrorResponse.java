package com.rsys.tunitshoppingcart.dto;

import lombok.Data;

/**
 * Error response class JSON response.
 */
@Data
public class ErrorResponse {
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    /**
     * Constructor is required for libraries that indirectly instantiate the class
     */
    public ErrorResponse() {
        // intentionally empty.
    }

}
