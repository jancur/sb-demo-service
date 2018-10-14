/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.domain.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.MoreObjects;

import lombok.Getter;
import lombok.Setter;

/**
 * title—The title field provides a brief title for the error condition. For
 * example, errors resulting as a result of input validation will have the title
 * “Validation Failure”. Similarly, an “Internal Server Error” will be used for
 * internal server errors.
 * 
 * status—The status field contains the HTTP status code for the current
 * request. Even though it is redundant to include status code in the response
 * body, it allows API clients to look for all the information that it needs to
 * troubleshoot in one place.
 * 
 * detail—The detail field contains a short description of the error. The
 * information in this field is typically human-readable and can be presented to
 * an end user.
 * 
 * timestamp—The time in milliseconds when the error occurred.
 * 
 * developerMessage—The developerMessage contains information such as exception
 * class name or stack trace that is relevant to developers. errors—The errors
 * field is used to report field validation errors.
 */
@Getter
@Setter
public final class ErrorDetail {
 
    private String title;
    private int status;
    private String detail;
    private long timeStamp;
    private String developerMessage;
    private Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("title", title).add("status", status).add("detail", detail)
                .add("timeStamp", timeStamp).add("developerMessage", developerMessage).toString();
    }
}
