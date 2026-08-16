package com.devsuperior.dscommerce.controllers.handlers;

import com.devsuperior.dscommerce.dto.CustomError;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.HtmlUtils;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String path = HtmlUtils.htmlEscape(request.getRequestURI());
        return ResponseEntity
                .status(status)
                .body(CustomError.of(status.value(), "Recurso não encontrado", path));
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = HtmlUtils.htmlEscape(request.getRequestURI());
        return ResponseEntity
                .status(status)
                .body(CustomError.of(status.value(), e.getMessage(), path));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> argumentNotValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        String path = HtmlUtils.htmlEscape(request.getRequestURI());
        return ResponseEntity
                .unprocessableEntity()
                .body(CustomError.validation("Dados inválidos", path, e.getBindingResult()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomError> database(ForbiddenException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        String path = HtmlUtils.htmlEscape(request.getRequestURI());
        return ResponseEntity
                .status(status)
                .body(CustomError.of(status.value(), e.getMessage(), path));
    }

}
