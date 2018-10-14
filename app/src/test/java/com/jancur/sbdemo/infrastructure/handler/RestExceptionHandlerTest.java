/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.infrastructure.handler;

import com.jancur.sbdemo.domain.DomainException;
import com.jancur.sbdemo.domain.model.ErrorDetail;
import com.jancur.sbdemo.test.SpringUnitTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test the RestExceptionHandler.
 */
public class RestExceptionHandlerTest extends SpringUnitTest {

    private static final String EXCEPTION_MESSAGE = "This is a test exception";

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

    @Test
    public final void testLoadErrorDetails() throws Exception {
        Exception exception = new Exception(EXCEPTION_MESSAGE);
        Method method = restExceptionHandler.getClass().getDeclaredMethod("loadErrorDetails", Exception.class,
                String.class, HttpStatus.class);
        method.setAccessible(true);
        ErrorDetail errorDetail = (ErrorDetail) method.invoke(restExceptionHandler, exception,
                                                              "This is a test Error Title", HttpStatus.NOT_FOUND);
        assertNotNull(errorDetail);
    }

    @Test
    public final void testHandleDomainException() {
        DomainException exception = new DomainException(HttpStatus.NOT_FOUND, EXCEPTION_MESSAGE);
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleDomainException(exception);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCode().value());
    }

    @Test
    public final void testHandleDomainExceptionNull() {
        DomainException exception = null;
        expectedException.expect(NullPointerException.class);
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleDomainException(exception);
        assertNotNull(responseEntity);
    }

    @Test
    public final void testHandleHttpMessageNotReadable() {
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("this is a test");
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.OK;
        WebRequest request = new ServletWebRequest(new MockHttpServletRequest());
        ResponseEntity<Object> responseEntity =
                restExceptionHandler.handleHttpMessageNotReadable(exception, headers, status, request);
        assertNotNull(responseEntity);
    }

}
