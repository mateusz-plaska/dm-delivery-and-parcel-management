package org.delivery.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.serializer.support.SerializationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorWrapper> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "Data not found";
        ErrorWrapper errorWrapper = new ErrorWrapper(message, request.getRequestURI(), status, ex.getMessage());
        logError(ex.getMessage(), request.getRequestURI(), ex);
        return new ResponseEntity<>(errorWrapper, status);
    }

    @ExceptionHandler(IllegalIdException.class)
    public ResponseEntity<ErrorWrapper> handleIllegalIdException(
            IllegalIdException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Invalid given id";
        ErrorWrapper errorWrapper = new ErrorWrapper(message, request.getRequestURI(), status, ex.getMessage());
        logError(ex.getMessage(), request.getRequestURI(), ex);
        return new ResponseEntity<>(errorWrapper, status);
    }

    @ExceptionHandler(InvalidCallException.class)
    public ResponseEntity<ErrorWrapper> handleInvalidCallException(
            InvalidCallException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Invalid method call";
        ErrorWrapper errorWrapper = new ErrorWrapper(message, request.getRequestURI(), status, ex.getMessage());
        logError(ex.getMessage(), request.getRequestURI(), ex);
        return new ResponseEntity<>(errorWrapper, status);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorWrapper> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Argument type mismatch for: " + ex.getName();
        ErrorWrapper errorWrapper = new ErrorWrapper(message, request.getRequestURI(), status, ex.getMessage());
        logError(ex.getMessage(), request.getRequestURI(), ex);
        return new ResponseEntity<>(errorWrapper, status);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorWrapper> handleJsonProcessingException(
            JsonProcessingException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Json processing error";
        ErrorWrapper errorWrapper = new ErrorWrapper(message, request.getRequestURI(), status, ex.getMessage());
        logError(ex.getMessage(), request.getRequestURI(), ex);
        return new ResponseEntity<>(errorWrapper, status);
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<ErrorWrapper> handleClassCastException(ClassCastException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Class cast exception";
        ErrorWrapper errorWrapper = new ErrorWrapper(message, request.getRequestURI(), status, ex.getMessage());
        logError(ex.getMessage(), request.getRequestURI(), ex);
        return new ResponseEntity<>(errorWrapper, status);
    }

    @ExceptionHandler(SerializationFailedException.class)
    public ResponseEntity<ErrorWrapper> handleSerializationFailedException(SerializationFailedException ex,
                                                                           HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Serialization failed exception";
        ErrorWrapper errorWrapper = new ErrorWrapper(ex.getMessage(), request.getRequestURI(), status, ex.getMessage());
        logError(message, request.getRequestURI(), ex);
        return new ResponseEntity<>(errorWrapper, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorWrapper> handleGeneralException(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An error has occurred.";
        ErrorWrapper errorWrapper = new ErrorWrapper(message, request.getRequestURI(), status, ex.getMessage());
        logError(ex.getMessage(), request.getRequestURI(), ex);
        return new ResponseEntity<>(errorWrapper, status);
    }

    private <T extends Exception> void logError(String message, String uri, T exception) {
        log.error("{} at uri: {}; Details: {}", message, uri, exception.getMessage());
    }
}
