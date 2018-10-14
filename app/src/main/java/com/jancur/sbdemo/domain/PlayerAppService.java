/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.domain;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.jancur.sbdemo.domain.model.PlayerDetail;
import com.jancur.sbdemo.repository.PlayerRepository;

@Component
public class PlayerAppService {

    private static final Logger LOGGER = LogManager.getLogger(PlayerAppService.class);

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Get an existing.
     * 
     * @param number the player number to get.
     * @return PlayerDetail the details
     */
    public final PlayerDetail getPlayer(final String number) {

        // TODO: Add an assert here, number != null

        PlayerDetail detail = null;
        try {
            // TODO: Need to check to see if number already exists in the
            // database with a status of active/inactive.
            // TODO: Add circuit breaker here?
            detail = playerRepository.findByNumber(number);
            if (null == detail) {
                LOGGER.debug("getPlayer: number={}; {}", number, "detail=null");
                throw new DomainException(HttpStatus.NOT_FOUND, "number " + number + " not found");
            }
        } catch (IllegalArgumentException | DataAccessException e) {
            LOGGER.error("getPlayer: ", e);
            throw new DomainException(HttpStatus.NOT_FOUND, "number " + number + " not found");
        }
        LOGGER.debug("getPlayer: number={}; {}", number, detail);
        return detail;
    }

    /**
     * Create a new.
     * 
     * @param detail required to create new instance.
     * @return PlayerDetail details
     */
    public final PlayerDetail addPlayer(final PlayerDetail detail) {

        // TODO: Add an assert here, detail != null
        PlayerDetail newDetail = null;
        try {
            newDetail = playerRepository.insert(detail);
        } catch (DataAccessException e) {
            String errorMessage = "Unable to add player " + detail;
            LOGGER.error("addPlayer: " + errorMessage, e);
            throw new DomainException(HttpStatus.NOT_FOUND, errorMessage);
        }
        LOGGER.debug("addPlayer: {}", newDetail);
        return newDetail;
    }

    /**
     * Delete.
     * 
     * @param id database id to delete.
     */
    public final void deletePlayer(final String id) {

        try {
            playerRepository.delete(id);
        } catch (DataAccessException e) {
            String errorMessage = "Unable to delete player with id " + id;
            LOGGER.error("deletePlayer: " + errorMessage, e);
            throw new DomainException(HttpStatus.NOT_FOUND, errorMessage);
        }
        LOGGER.debug("deletePlayer: {}", id);
        return;
    }

    /**
     * Search box by any box field(s).
     * 
     * @return List of BoxDetail objects
     */
    public final List<PlayerDetail> getAllPlayers() {
        List<PlayerDetail> playerList = null;
        try {
            playerList = playerRepository.findAll();
            if (null == playerList) {
                LOGGER.debug("getAllPlayers: playerList=null");
                throw new DomainException(HttpStatus.NOT_FOUND, "No players found");
            }
        } catch (IllegalArgumentException | DataAccessException e) {
            LOGGER.error("getAllPlayers: ", e);
            throw new DomainException(HttpStatus.NOT_FOUND, "No players found");
        }
        LOGGER.debug("getAllPlayers: {}", playerList);
        return playerList;
    }
}
