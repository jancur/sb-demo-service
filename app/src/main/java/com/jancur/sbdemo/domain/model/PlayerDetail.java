/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package com.jancur.sbdemo.domain.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.google.common.base.MoreObjects;

import lombok.Getter;
import lombok.Setter;

/**
 * Holds the details.
 */
@Setter
@Getter
public class PlayerDetail {

    @Id
    private String id;

    @NotNull
    private String number;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String status;

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("number", number).add("firstName", firstName)
                .add("lastName", lastName).add("status", status).toString();
    }

}
