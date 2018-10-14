/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.infrastructure.filter;

import com.google.common.base.MoreObjects;

import lombok.Getter;
import lombok.Setter;

/**
 */
@Getter
@Setter
public class HeaderDetailImpl implements HeaderDetail {

    private String nordApiVersion;
    private String correlationId;

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("nordApiVersion", nordApiVersion)
                .add("correlationId", correlationId).toString();
    }

}
