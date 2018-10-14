/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.domain.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;

import lombok.Getter;
import lombok.Setter;

/**
 * Holds the details.
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "service.context")
public class ServiceContext {

    // @Id
    private String applicationId;

    // @NotNull
    private String businessAction;

    // @NotNull
    private String businessTransactionId;

    // @NotNull
    private String environmentId;

    // @NotNull
    private String resourceId;

    // @NotNull
    private String systemId;

    // @NotNull
    private String userId;

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("applicationId", applicationId)
                .add("businessAction", businessAction).add("businessTranId", businessTransactionId)
                .add("environmentId", environmentId).add("resourceId", resourceId).add("systemId", systemId)
                .add("userId", userId).toString();
    }

}

