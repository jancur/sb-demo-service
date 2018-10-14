/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.infrastructure.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.common.base.Preconditions;
import com.jancur.sbdemo.domain.DomainException;
import com.jancur.sbdemo.domain.model.ErrorDetail;
import com.jancur.sbdemo.domain.model.ValidationError;

/**
 * Handler for response exceptions.
 * 
 * Provides a consistent error response to the consumer.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(RestExceptionHandler.class);

    @Inject
    private MessageSource messageSource;

    /**
     * Handle any DomainExceptions thrown during processing of the request.
     * 
     * Exception details will be captured in an ErrorDetail object.
     * 
     * @param exception DomainException. The response Http status code will be
     *        extracted from the exception details.
     * @return ResponseEntity
     */
    @ExceptionHandler(DomainException.class)
    public final ResponseEntity<Object> handleDomainException(final DomainException exception) {
        Preconditions.checkNotNull(exception, "Exception must not be null");
        HttpStatus status = exception.getStatusCode();
        ErrorDetail errorDetail = loadErrorDetails(exception, exception.getStatusCode().toString(), status);
        LOGGER.info("handleDomainException: {}", errorDetail);
        return new ResponseEntity<>(errorDetail, null, exception.getStatusCode());
    }

    @Override
    protected final ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException exception,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        ErrorDetail errorDetail = loadErrorDetails(exception, "Message Not Readable", status);
        LOGGER.info("handleHttpMessageNotReadable: {}", errorDetail);
        return handleExceptionInternal(exception, errorDetail, headers, status, request);
    }

    /**
     * Create an ErrorDetails object.
     * 
     * @param exception Associated exception.
     * @param title Error title.
     * @param httpStatus Associated http status code.
     * @return ErrorDetail
     */
    private ErrorDetail loadErrorDetails(final Exception exception, final String title, final HttpStatus httpStatus) {
        Preconditions.checkNotNull(exception, "Exception must not be null");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(httpStatus.value());
        errorDetail.setTitle(title);
        errorDetail.setDetail(exception.getMessage());
        errorDetail.setDeveloperMessage(exception.getClass().getName());
        return errorDetail;
    }

    @Override
    public final ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException notValidException, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();

        // Populate errorDetail instance
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Validation Failed");
        errorDetail.setDetail("Input validation failed");
        errorDetail.setDeveloperMessage(notValidException.getClass().getName());

        // Create ValidationError instances
        List<FieldError> fieldErrors = notValidException.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {

            List<ValidationError> validationErrorList = errorDetail.getErrors().get(fieldError.getField());
            if (validationErrorList == null) {
                validationErrorList = new ArrayList<ValidationError>();
                errorDetail.getErrors().put(fieldError.getField(), validationErrorList);
            }
            ValidationError validationError =
                    new ValidationError(fieldError.getCode(), messageSource.getMessage(fieldError, null));
            validationErrorList.add(validationError);
        }
        LOGGER.info("handleMethodArgumentNotValid: {}", errorDetail);

        return handleExceptionInternal(notValidException, errorDetail, headers, status, request);
    }

}
