package com.oracle.football.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.jsoup.select.Selector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ApiError> handleException(ResourceNotFoundException e,
      HttpServletRequest request) {
    ApiError apiError = new ApiError(
        request.getRequestURI(),
        e.getMessage(),
        HttpStatus.NOT_FOUND.value(),
        LocalDateTime.now()
    );
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InsufficientAuthenticationException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<ApiError> handleException(InsufficientAuthenticationException e,
      HttpServletRequest request) {
    ApiError apiError = new ApiError(
        request.getRequestURI(),
        e.getMessage(),
        HttpStatus.FORBIDDEN.value(),
        LocalDateTime.now()
    );
    return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ApiError> handleException(BadCredentialsException e,
      HttpServletRequest request) {
    ApiError apiError = new ApiError(
        request.getRequestURI(),
        e.getMessage(),
        HttpStatus.UNAUTHORIZED.value(),
        LocalDateTime.now()
    );
    return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ApiError> handleException(Exception e,
      HttpServletRequest request) {
    ApiError apiError = new ApiError(
        request.getRequestURI(),
        e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        LocalDateTime.now()
    );
    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Selector.SelectorParseException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ApiError> handleException(Selector.SelectorParseException e,
      HttpServletRequest request) {
    ApiError apiError = new ApiError(
        request.getRequestURI(),
        e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        LocalDateTime.now()
    );
    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
