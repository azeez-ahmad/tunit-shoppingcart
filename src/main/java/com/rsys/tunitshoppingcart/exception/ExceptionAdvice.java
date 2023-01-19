package com.rsys.tunitshoppingcart.exception;

import com.rsys.tunitshoppingcart.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> internalErrorException(HttpServletRequest request, Exception e) {
        ErrorResponse errorResponse = getErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse getErrorResponse(HttpServletRequest request, HttpStatus status, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(status.value());
        errorResponse.setError(status.getReasonPhrase());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setTimestamp(System.currentTimeMillis());
        errorResponse.setMessage(message);

        return errorResponse;
    }
}
