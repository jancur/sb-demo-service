/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.jancur.sbdemo.test.SpringUnitTest;
import com.jancur.sbdemo.test.TestDetails;

/**
 * Test the DomainException class.
 */
public class DomainExceptionTest extends SpringUnitTest {
    @Test
    @TestDetails(context = "DomainException(statusCode)", it = "Sets status code")
    public final void testSetStatusCodeOnly() throws Exception {
        DomainException exception = new DomainException(HttpStatus.OK);
        assertEquals(HttpStatus.OK, exception.getStatusCode());
    }

    @Test
    @TestDetails(context = "DomainException(statusCode, message)", it = "Sets status code")
    public final void testSetStatusCodeMessage() throws Exception {
        DomainException exception = new DomainException(HttpStatus.OK, "");
        assertEquals(HttpStatus.OK, exception.getStatusCode());
    }

}
