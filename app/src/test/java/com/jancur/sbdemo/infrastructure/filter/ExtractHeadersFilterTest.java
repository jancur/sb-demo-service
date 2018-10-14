/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>

 */
package com.jancur.sbdemo.infrastructure.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.jancur.sbdemo.test.SpringUnitTest;

/**
 * Test the header extraction filter class.
 */
public class ExtractHeadersFilterTest extends SpringUnitTest {

    private static final String NORD_API_VERSION_TEST = "2.1";
    private static final String TRACE_CONTEXT_VALID = "32468kbkhjdsythgkhjhjeythdjhfu";
    private static final String NULL_STRING = null;
    private static final String EMPTY_STRING = "";

    @Test
    public final void testGetCorrelationId() throws Exception {
        ExtractHeadersFilter filter = new ExtractHeadersFilter();
        Method method = filter.getClass().getDeclaredMethod("setCorrelationId", String.class);
        method.setAccessible(true);
        String result = (String) method.invoke(filter, TRACE_CONTEXT_VALID);
        assertEquals(result, TRACE_CONTEXT_VALID);
    }

    @Test
    public final void testGetCorrelationIdNull() throws Exception {
        ExtractHeadersFilter filter = new ExtractHeadersFilter();
        Method method = filter.getClass().getDeclaredMethod("setCorrelationId", String.class);
        method.setAccessible(true);
        String result = (String) method.invoke(filter, NULL_STRING);
        assertNotNull(result);
    }

    @Test
    public final void testGetCorrelationIdEmpty() throws Exception {
        ExtractHeadersFilter filter = new ExtractHeadersFilter();
        Method method = filter.getClass().getDeclaredMethod("setCorrelationId", String.class);
        method.setAccessible(true);
        String result = (String) method.invoke(filter, EMPTY_STRING);
        assertNotNull(result);
        assertNotEquals(EMPTY_STRING, result);
    }

    @Test
    public final void testFilterInternalNoHeaderValues() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        ExtractHeadersFilter filter = new ExtractHeadersFilter();
        HeaderDetail headerDetailImpl = new HeaderDetailImpl();
        Field headerDetail = ExtractHeadersFilter.class.getDeclaredField("headerDetail");
        headerDetail.setAccessible(true);
        headerDetail.set(filter, headerDetailImpl);

        filter.doFilterInternal(request, response, chain);
        assertNotNull(headerDetailImpl.getCorrelationId());
        assertEquals(ExtractHeadersFilter.NORD_API_VERSION_DEFAULT, headerDetailImpl.getNordApiVersion());
    }

    @Test
    public final void testFilterInternalEmptyHeaderValues() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        request.addHeader("test", "test");

        ExtractHeadersFilter filter = new ExtractHeadersFilter();
        HeaderDetail headerDetailImpl = new HeaderDetailImpl();
        Field headerDetail = ExtractHeadersFilter.class.getDeclaredField("headerDetail");
        headerDetail.setAccessible(true);
        headerDetail.set(filter, headerDetailImpl);

        filter.doFilterInternal(request, response, chain);
        assertNotNull(headerDetailImpl.getCorrelationId());
        assertEquals(ExtractHeadersFilter.NORD_API_VERSION_DEFAULT, headerDetailImpl.getNordApiVersion());
    }

    @Test
    public final void testFilterInternalWithHeaderValues() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        request.addHeader(ExtractHeadersFilter.NORD_API_VERSION_KEY, NORD_API_VERSION_TEST);
        UUID uuid = UUID.randomUUID();
        String correlationId = uuid.toString();
        request.addHeader(ExtractHeadersFilter.CORRELATION_ID_KEY, correlationId);

        ExtractHeadersFilter filter = new ExtractHeadersFilter();
        HeaderDetail headerDetailImpl = new HeaderDetailImpl();
        Field headerDetail = ExtractHeadersFilter.class.getDeclaredField("headerDetail");
        headerDetail.setAccessible(true);
        headerDetail.set(filter, headerDetailImpl);

        filter.doFilterInternal(request, response, chain);
        assertEquals(NORD_API_VERSION_TEST, headerDetailImpl.getNordApiVersion());
        assertEquals(correlationId, headerDetailImpl.getCorrelationId());
    }
}
