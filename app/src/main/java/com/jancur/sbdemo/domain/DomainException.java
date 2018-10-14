/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.domain;

import org.springframework.http.HttpStatus;

import com.google.common.base.MoreObjects;

import lombok.Getter;

/**
 * General runtime exception for use by the App Service classes.
 */
@Getter
public class DomainException extends RuntimeException {

    /**
     * Serializable Version Number.
     */
    private static final long serialVersionUID = 1L;

    /* This error code */
    private final HttpStatus statusCode;

    /**
     * DomainException DomainException.
     * 
     * @param statusCode The status code associated with the Exception.
     */
    public DomainException(final HttpStatus statusCode) {
        super();
        this.statusCode = statusCode;
    }

    /**
     * DomainException DomainException.
     * 
     * @param statusCode The status code associated with the Exception.
     * @param message message
     */
    public DomainException(final HttpStatus statusCode, final String message) {
        super(message);
        this.statusCode = statusCode;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("super", super.toString()).add("statusCode", statusCode).toString();
    }
}

