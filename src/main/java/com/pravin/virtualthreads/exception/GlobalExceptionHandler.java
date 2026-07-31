package com.pravin.virtualthreads.exception;
import io.opentelemetry.api.trace.Span;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        String traceId = Span.current().getSpanContext().getTraceId();
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Internal Server Error");
        problem.setDetail("Something went wrong. Please contact support.");
        problem.setProperty("traceId", traceId);
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Validation failed");
        problem.setDetail("One or more fields are invalid");
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage, (existingMessage, newMessage) -> existingMessage
        ));
        problem.setProperty("errors", errors);
        log.info("Validation failed: - Status: {}, Title: '{}', Detail: '{}', Instance:'{}'",
                problem.getStatus(),
                problem.getTitle(),
                problem.getDetail(),
                problem.getInstance(),
                ex);
        return problem;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Validation failed");
        problem.setDetail("One or more fields are invalid");
        Map<String, String> errors = ex.getConstraintViolations().stream().collect(Collectors.toMap(
                violation -> {
                    String propertyPath = violation.getPropertyPath().toString();
                    return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
                },
                ConstraintViolation::getMessage, (existingValue, newValue) -> existingValue
        ));
        problem.setProperty("errors", errors);
        log.info("Validation failed: - Status: {}, Title: '{}', Detail: '{}', Instance:'{}'",
                problem.getStatus(),
                problem.getTitle(),
                problem.getDetail(),
                problem.getInstance(),
                ex);
        return problem;
    }

}