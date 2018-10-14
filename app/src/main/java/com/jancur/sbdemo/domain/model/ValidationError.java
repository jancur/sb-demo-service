/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.domain.model;

import com.google.common.base.MoreObjects;

import lombok.Getter;

/**
 * Holds details on validation errors.
 */
@Getter
public final class ValidationError {

    private final String code;
    private final String message;

    /**
     * Create new error object.
     * 
     * @param code Validation error code.
     * @param message Validation error message.
     */
    public ValidationError(final String code, final String message) {
        super();
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("code", code).add("message", message).toString();
    }

}
