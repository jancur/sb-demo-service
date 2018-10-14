/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.controller;

import com.google.common.base.Stopwatch;

import com.jancur.sbdemo.domain.PlayerAppService;
import com.jancur.sbdemo.domain.model.PlayerDetail;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

/**
 * Controller for Service endpoints.
 */
@RestController
@RequestMapping("/players")
@Api(basePath = "/players", value = "player", description = "Endpoint to manage Player details.")
public class PlayerController {

    private static final Logger LOGGER = LogManager.getLogger(PlayerController.class);

    @Autowired
    private PlayerAppService appService;
    
    /**
     * Service endpoint to get all players.
     * 
     * @return PlayerDetail the details
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public final ResponseEntity<List<PlayerDetail>> getAllPlayers() {
        Stopwatch timer = Stopwatch.createStarted();
        List<PlayerDetail> playerList = appService.getAllPlayers();
        LOGGER.info("getAllPlayers: executionTime={}, {}", timer.stop(), playerList);
        return new ResponseEntity<List<PlayerDetail>>(playerList, HttpStatus.OK);
    }

    /**
     * Service endpoint to get player details.
     * 
     * @param number the number.
     * @return PlayerDetail the details
     */
    @RequestMapping(value = "/{number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public final ResponseEntity<PlayerDetail> getPlayer(@PathVariable final String number) {
        Stopwatch timer = Stopwatch.createStarted();
        PlayerDetail detail = appService.getPlayer(number);
        LOGGER.info("getPlayer: number={}; executionTime={}, {}", number, timer.stop(), detail);
        return new ResponseEntity<PlayerDetail>(detail, HttpStatus.OK);
    }

    /**
     * Service endpoint to get player details.
     * 
     * @param id the record id.
     * @return PlayerDetail the details
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public final ResponseEntity<String> deletePlayer(@PathVariable final String id) {
        Stopwatch timer = Stopwatch.createStarted();
        appService.deletePlayer(id);
        LOGGER.info("deletePlayer: id={}; executionTime={}", id, timer.stop());
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * Service endpoint to create a new player.
     * 
     * @param detail the details
     * @return PlayerDetail of the newly created instance.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Adds a new Player", notes = "The newly created Player Object will be returned.",
            response = PlayerDetail.class)
    @ResponseBody
    public final ResponseEntity<PlayerDetail> addPlayer(@Valid @RequestBody final PlayerDetail detail) {
        Stopwatch timer = Stopwatch.createStarted();
        PlayerDetail newDetail = appService.addPlayer(detail);
        LOGGER.info("addPlayer: executionTime={}, {}", timer.stop(), newDetail);
        return new ResponseEntity<PlayerDetail>(detail, HttpStatus.CREATED);
    }

}
