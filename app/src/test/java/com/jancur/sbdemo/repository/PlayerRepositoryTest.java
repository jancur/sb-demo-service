/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jancur.sbdemo.domain.model.PlayerDetail;
import com.jancur.sbdemo.test.PlayerDetailAssembler;
import com.jancur.sbdemo.test.SpringUnitTest;

public class PlayerRepositoryTest extends SpringUnitTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public final void setup() {
        importJSON("playerDetail", "src/test/resources/playerDetail.json");
    }

    @Test
    public final void findByNumber() {
        PlayerDetail playerDetail = playerRepository.findByNumber("3");
        assertEquals("3", playerDetail.getNumber());
    }

    @Test
    public final void findByNumberNull() {
        PlayerDetail playerDetail = playerRepository.findByNumber(null);
        assertNull(playerDetail);
    }

    @Test
    public final void findByNumberInvalid() {
        PlayerDetail playerDetail = playerRepository.findByNumber("999");
        assertNull(playerDetail);
    }
    
    @Test
    public final void findAll() {
        List<PlayerDetail> playerList = playerRepository.findAll();
        assertNotNull(playerList);
    }

    @Test
    public final void insertNew() {
        PlayerDetail newPlayerDetail = new PlayerDetailAssembler().withNumber("31").withFirstName("Kam")
                                                                  .withLastName("Chancellor").withStatus("0").assemble();
        PlayerDetail playerDetail = playerRepository.insert(newPlayerDetail);
        assertNotNull(playerDetail);
        assertEquals(newPlayerDetail.getId(), playerDetail.getId());
    }
}
