/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.jancur.sbdemo.test.SpringUnitTest;

/**
 * Test Status codes.
 */
public class PlayerStatusTest extends SpringUnitTest {

    @Test
    public final void testValid() throws Exception {
        PlayerStatus value = PlayerStatus.ACTIVE;
        assertNotNull(value);
    }

    @Test
    public final void testEqual() {
        PlayerStatus value = PlayerStatus.PROBABLE;
        PlayerStatus value2 = PlayerStatus.PROBABLE;
        boolean isSame = value.equals(value2);
        assertTrue(isSame);
        assertSame(value, value2);
        assertEquals(value, value2);
    }

    @Test
    public final void testNotEqual() {
        if (PlayerStatus.ACTIVE == PlayerStatus.INJURED_RESERVE) {
            fail("not equal");
        }
        PlayerStatus value = PlayerStatus.NOT_ON_TEAM;
        PlayerStatus value2 = PlayerStatus.PHYSICALLY_UNABLE_TO_PERFORM;
        if (value == value2) {
            fail("equal");
        }
        boolean isSame = value.equals(value2);
        assertFalse(isSame);
        assertNotSame(value, value2);
    }

    @Test
    public final void testValueOf() {
        PlayerStatus.valueOf("ACTIVE");
        PlayerStatus.valueOf("PRACTICE_SQUAD");
    }

    @Test
    public final void testFromCode() {
        PlayerStatus playerStatus = PlayerStatus.fromCode("ACT");
        assertSame(playerStatus, PlayerStatus.ACTIVE);
    }

    @Test
    public final void testFromCodeUnknown() {
        PlayerStatus playerStatus = PlayerStatus.fromCode("XYZ");
        assertSame(playerStatus, PlayerStatus.UNKNOWN);
    }
}
