package com.san.recipefinder.service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
class RestExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionAdvice.class);

    @ExceptionHandler({RecipeNotFoundException.class, EmptyResultDataAccessException.class})
    ResponseEntity<String> handleRecipeNotFoundException(
            Exception exception,
            HttpServletRequest request
    ) {
        LOGGER.error("Handle exception", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("No recipe found.");
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleException(
            Exception exception,
            HttpServletRequest request
    ) {
        LOGGER.error("Handle exception", exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}

