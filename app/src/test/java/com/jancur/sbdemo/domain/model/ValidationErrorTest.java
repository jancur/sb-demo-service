/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.domain.model;

import com.jancur.sbdemo.test.SpringUnitTest;
import com.jancur.sbdemo.test.TestDetails;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the ValidationError class.
 */
public class ValidationErrorTest extends SpringUnitTest {
    private static final String MESSAGE_VALID = "message";
    private static final String CODE_VALID = "code";

    private ValidationError error;

    @Before
    public final void setUp() {
        error = new ValidationError(CODE_VALID, MESSAGE_VALID);
    }

    @Test
    @TestDetails(context = "ValidationError()", it = "sets the code")
    public final void testValidationErrorSetsCode() {
        assertEquals(error.getCode(), CODE_VALID);
    }

    @Test
    @TestDetails(context = "ValidationError()", it = "sets the message")
    public final void testValidationErrorSetsMessage() {
        assertEquals(error.getMessage(), MESSAGE_VALID);
    }

    @Test
    @TestDetails(context = "ValidationError()", it = "sets the message")
    public final void testToString() {
        String toString = error.toString();
        assertEquals(toString, "ValidationError{code=" + CODE_VALID + ", message=" + MESSAGE_VALID + "}");
    }
}
