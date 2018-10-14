/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.infrastructure.filter;

/**
 * Holds details of the request header that may be need by other parts of the
 * service.
 */
public interface HeaderDetail {

    /**
     * Get the CorrelationId associated with the Request header.
     * 
     * @return CorrelationId the header trace context ID
     */
    String getCorrelationId();

    /**
     * Set the CorrelationId associated with the Request header.
     * 
     * @param correlationId the header trace context ID.
     */
    void setCorrelationId(String correlationId);

    /**
     * Get the NordApiVersion associated with the Request header.
     * 
     * @return NordApiVersion the header version number.
     */
    String getNordApiVersion();

    /**
     * * Set the NordApiVersion associated with the Request header.
     * 
     * @param nordApiVersion the header version number
     */
    void setNordApiVersion(String nordApiVersion);

}
