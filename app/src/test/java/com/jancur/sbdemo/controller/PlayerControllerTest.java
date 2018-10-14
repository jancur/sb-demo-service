/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.controller;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.http.HttpStatus;

import com.jancur.sbdemo.domain.model.PlayerDetail;
import com.jancur.sbdemo.repository.PlayerRepository;
import com.jancur.sbdemo.test.PlayerDetailAssembler;
import com.jancur.sbdemo.test.SpringUnitTest;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;

@IntegrationTest("server.port:0")
public class PlayerControllerTest extends SpringUnitTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Value("${local.server.port}")
    private int port;

    @Before
    public final void setUp() {
        playerRepository.deleteAll();
        importJSON("playerDetail", "src/test/resources/playerDetail.json");
        RestAssured.port = port;
    }

    @Test
    public final void testGetAll() {
        String json = get("/players").asString();
        JsonPath jsonPath = new JsonPath(json);
        List<String> names = jsonPath.get("firstName");
        assertEquals(names.get(2), "Marshawn");
    }

    @Test
    public final void testGet() {
        // @formatter:off
        when().
            get("/players/{number}", "3").
        then().
            statusCode(HttpStatus.OK.value()).
            body("firstName", Matchers.is("Russell"),
                 "status", Matchers.is(PlayerStatus.ACTIVE.getCode()));
        // @formatter:on
    }
    
    @Test
    public final void testDelete() {
        // @formatter:off
        when().
            delete("/players/{id}", "8e5884y5jhdf").
        then().
            statusCode(HttpStatus.OK.value());
        // @formatter:on
    }

    @Test
    public final void testPost() {
        PlayerDetail detail = new PlayerDetailAssembler().assemble();
        // @formatter:off
        given().
            contentType("application/json").
            and().
            body(detail).
        when().
            post("/players").
        then().
            statusCode(HttpStatus.CREATED.value());
        // @formatter:on
    }

}
