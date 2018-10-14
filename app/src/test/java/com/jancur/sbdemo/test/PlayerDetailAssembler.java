/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.test;


import com.jancur.sbdemo.controller.PlayerStatus;
import com.jancur.sbdemo.domain.model.PlayerDetail;

/**
 * Assemble a details DTO for testing.
 */
public class PlayerDetailAssembler extends Assembler<PlayerDetail> {

    private String number = "1234";
    private String firstName = "James";
    private String lastName = "Bacus";
    private String status = PlayerStatus.ACTIVE.getCode();

    @Override
    protected final PlayerDetail internalAssemble() {
        PlayerDetail detail = new PlayerDetail();

        detail.setNumber(number);
        detail.setFirstName(firstName);
        detail.setLastName(lastName);
        detail.setStatus(status);

        return detail;
    }

    public final PlayerDetailAssembler withNumber(final String number) {
        this.number = number;
        return this;
    }

    public final PlayerDetailAssembler withFirstName(final String name) {
        this.firstName = name;
        return this;
    }

    public final PlayerDetailAssembler withLastName(final String name) {
        this.lastName = name;
        return this;
    }

    public final PlayerDetailAssembler withStatus(final String status) {
        this.status = status;
        return this;
    }
}

