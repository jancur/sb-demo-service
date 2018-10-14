/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.infrastructure.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.RequestContextFilter;

import com.google.common.base.Strings;

/**
 * Filter class to extract the desired Header values.
 * 
 * Values are added to an object which is set in the Request Scope.
 */
public class ExtractHeadersFilter extends RequestContextFilter {

    private static final Logger LOGGER = LogManager.getLogger(ExtractHeadersFilter.class);

    public static final String NORD_API_VERSION_KEY = "nordapiversion";
    public static final String NORD_API_VERSION_DEFAULT = "1.0";
    public static final String CORRELATION_ID_KEY = "correlationid";

    @Autowired
    private HeaderDetail headerDetail;

    @Override
    protected final void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain) throws ServletException, IOException {

        String nordApiVersion = NORD_API_VERSION_DEFAULT;
        String correlationId = null;
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            LOGGER.debug("doFilterInternal: key={}, value={}", key, value);
            if (key.equalsIgnoreCase(NORD_API_VERSION_KEY)) {
                nordApiVersion = value;
            } else if (key.equalsIgnoreCase(CORRELATION_ID_KEY)) {
                correlationId = value;
            }
        }

        headerDetail.setNordApiVersion(nordApiVersion);
        headerDetail.setCorrelationId(setCorrelationId(correlationId));

        // Add values to log4j thread context
        ThreadContext.put(CORRELATION_ID_KEY, headerDetail.getCorrelationId());

        LOGGER.debug("doFilterInternal: {}", headerDetail);
        chain.doFilter(request, response);
    }

    /**
     * Extract the CorrelationId header value.
     * 
     * If the value is null or empty a new ID will be generated.
     * 
     * @param headerValue the header value. May be null.
     * @return CorrelationId value
     */
    private String setCorrelationId(final String headerValue) {
        String correlationId = headerValue;
        if (Strings.isNullOrEmpty(correlationId)) {

            // Create a random UUID (Universally unique identifier).
            UUID uuid = UUID.randomUUID();
            correlationId = uuid.toString();
        }
        return correlationId;
    }

}
