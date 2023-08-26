package flatrock.technology.productsservice.controllers;

import flatrock.technology.productsservice.model.exceptions.EntityAlreadyExists;
import flatrock.technology.productsservice.model.exceptions.ProductCreationException;
import flatrock.technology.productsservice.model.exceptions.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("RequestUrl: {}, raised exception: {}", ((ServletWebRequest)request).getRequest().getRequestURL(), ex.getMessage(), ex);
        var errorMessages = ex
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        var problemDetails = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Invalid request content.");
        problemDetails.setTitle("validations failed");
        problemDetails.setProperty("errors", errorMessages);
        return ResponseEntity.status(BAD_REQUEST).body(problemDetails);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail handleProductNotFoundException(ProductNotFoundException ex, HttpServletRequest request) {
        log.error("RequestUrl: {}, raised exception: {}", request.getRequestURL(), ex.getMessage(), ex);
        var response = ProblemDetail.forStatusAndDetail(NOT_FOUND, ex.getMessage());
        response.setTitle("product not fund");
        return response;
    }

    @ExceptionHandler({ProductCreationException.class, EntityAlreadyExists.class})
    public ProblemDetail handleProductCreationException(ProductCreationException ex, HttpServletRequest request) {
        log.error("RequestUrl: {}, raised exception: {}", request.getRequestURL(), ex.getMessage(), ex);
        var response = ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage());
        response.setTitle("product creation");
        return response;
    }

    @ExceptionHandler({AuthenticationException.class, AccessDeniedException.class})
    public ProblemDetail handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        log.error("RequestUrl: {}, raised exception: {}", request.getRequestURL(), ex.getMessage(), ex);
        var response = ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage());
        response.setTitle("security exception");
        return response;
    }
}
