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
 * Test the ErrorDetail class.
 */
public class ErrorDetailTest extends SpringUnitTest {
    private ErrorDetail error;

    @Before
    public final void setUp() {
        error = new ErrorDetail();
    }

    @Test
    @TestDetails(context = "toString()", it = "includes nordApiVersion and correlationId")
    public final void testGetCorrelationId() throws Exception {
        String toString = error.toString();

        assertEquals("ErrorDetail{title=null, status=0, detail=null, timeStamp=0, developerMessage=null}", toString);
    }

    @Test
    @TestDetails(context = "getters/setters", it = "can get/set title")
    public final void testTitleGetterSetter() {
        error.setTitle("title");
        assertEquals("title", error.getTitle());
    }

    @Test
    @TestDetails(context = "getters/setters", it = "can get/set status")
    public final void testStatusGetterSetter() {
        error.setStatus(1);
        assertEquals(1, error.getStatus());
    }

    @Test
    @TestDetails(context = "getters/setters", it = "can get/set detail")
    public final void testDetailGetterSetter() {
        error.setDetail("detail");
        assertEquals("detail", error.getDetail());
    }

    @Test
    @TestDetails(context = "getters/setters", it = "can get/set timeStamp")
    public final void testTimeStampGetterSetter() {
        error.setTimeStamp(1);
        assertEquals(1, error.getTimeStamp());
    }

    @Test
    @TestDetails(context = "getters/setters", it = "can get/set developerMessage")
    public final void testDeveloperMessageGetterSetter() {
        error.setDeveloperMessage("developerMessage");
        assertEquals("developerMessage", error.getDeveloperMessage());
    }
}
