package com.san.recipefinder.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
class RestExceptionAdvice {

    @ExceptionHandler(RecipeNotFoundException.class)
    ResponseEntity<String> handleRecipeNotFoundException(
            RecipeNotFoundException recipeNotFoundException,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(recipeNotFoundException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleException(
            Exception exception,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}

