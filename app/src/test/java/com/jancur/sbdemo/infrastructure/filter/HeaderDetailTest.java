/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.infrastructure.filter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jancur.sbdemo.test.SpringUnitTest;
import com.jancur.sbdemo.test.TestDetails;

/**
 * Test the HeaderDetail class.
 */
public class HeaderDetailTest extends SpringUnitTest {
    private HeaderDetailImpl header;

    @Before
    public final void setUp() {
        header = new HeaderDetailImpl();
    }

    @Test
    @TestDetails(context = "toString()", it = "includes nordApiVersion and CorrelationId")
    public final void testGetCorrelationId() throws Exception {
        header.setCorrelationId("id");
        header.setNordApiVersion("version");
        String toString = header.toString();
        assertEquals(toString, "HeaderDetailImpl{nordApiVersion=version, correlationId=id}");
    }

    @Test
    @TestDetails(context = "getters/setters", it = "can get/set nordApiVersion")
    public final void testNordApiVersionGetterSetter() {
        header.setNordApiVersion("1.0");
        assertEquals("1.0", header.getNordApiVersion());
    }

    @Test
    @TestDetails(context = "getters/setters", it = "can get/set CorrelationId")
    public final void testCorrelationIdGetterSetter() {
        header.setCorrelationId("1.0");
        assertEquals("1.0", header.getCorrelationId());
    }
}
