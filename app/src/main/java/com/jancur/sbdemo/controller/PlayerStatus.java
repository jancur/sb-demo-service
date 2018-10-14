/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.MoreObjects;

/**
 * Enumeration that provides a mapping between domain status codes and HTTP
 * status codes.
 */
public enum PlayerStatus {

    ACTIVE("ACT", ""),
    PROBABLE("PRO", ""),
    QUESTIONABLE("QUE", ""),
    DOUBTFUL("DOU", ""),
    OUT("OUT", ""),
    INJURED_RESERVE("INR", ""),
    PRACTICE_SQUAD("PRA", ""),
    PHYSICALLY_UNABLE_TO_PERFORM("PUP", ""),
    NOT_ON_TEAM("NOT", ""),
    UNKNOWN("UNK", "");

    private String code;
    private String description;

    private static final Map<String, PlayerStatus> VALUE_TO_ENUM = new HashMap<String, PlayerStatus>();

    static {
        for (PlayerStatus status : values()) {
            VALUE_TO_ENUM.put(status.getCode(), status);
        }
    }

    /**
     * Constructor.
     * 
     * @param code domain status code
     * @param description message
     */
    PlayerStatus(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Convert a code into enum object.
     * 
     * Returns UNKNOWN if not found.
     * 
     * @param code the code
     * @return PlayerStatus
     */
    public static PlayerStatus fromCode(final String code) {
        PlayerStatus playerStatus = VALUE_TO_ENUM.get(code);
        if (null == playerStatus) {
            return UNKNOWN;
        }
        return playerStatus;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("code", code).add("message", description).toString() + ". ";
    }
}


