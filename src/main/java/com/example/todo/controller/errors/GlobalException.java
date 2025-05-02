package com.example.todo.controller.errors;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.todo.domain.ApiResponse;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(NoSuchElementException ex) {
      var result= new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "handlenotfound", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
// Hanle rest exception

@ExceptionHandler(Exception.class)
public ResponseEntity<ApiResponse<?>> handleAlllException(Exception ex) {
  var result= new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "handleallexceptionfound", null, ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
}
}
