package org.delivery.exception;

import org.springframework.http.HttpStatus;

public record ErrorWrapper(String errorMessage, String uri, HttpStatus occurredStatus, String details) {}
