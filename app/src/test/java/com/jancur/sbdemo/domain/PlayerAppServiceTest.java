/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.http.HttpStatus;

import com.jancur.sbdemo.domain.model.PlayerDetail;
import com.jancur.sbdemo.repository.PlayerRepository;
import com.jancur.sbdemo.test.PlayerDetailAssembler;
import com.jancur.sbdemo.test.SpringUnitTest;

/**
 * Test the App Service.
 */
public class PlayerAppServiceTest extends SpringUnitTest {

    private static final String ID_VALID = "1234-abcd";

    @InjectMocks
    private PlayerAppService appServiceImpl = new PlayerAppService();

    @Mock
    private PlayerRepository playerRepository;

    @Before
    public final void setUp() {
        // create mock objects
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public final void testGetPlayerValid() {
        PlayerDetail detail = new PlayerDetailAssembler().withNumber(ID_VALID).assemble();
        Mockito.when(playerRepository.findByNumber(ID_VALID)).thenReturn(detail);
        PlayerDetail details = appServiceImpl.getPlayer(ID_VALID);
        assertNotNull(details);
        assertEquals(detail.getNumber(), ID_VALID);
    }
    
    @Test
    @Ignore
    public final void testDeletePlayerValid() {
        PlayerDetail detail = new PlayerDetailAssembler().withNumber(ID_VALID).assemble();
        Mockito.when(playerRepository.findByNumber(ID_VALID)).thenReturn(detail);
        appServiceImpl.deletePlayer(ID_VALID);
    }
    
    @Test
    @Ignore
    public final void testDeletePlayerInValid() {
        PlayerDetail detail = new PlayerDetailAssembler().withNumber(ID_VALID).assemble();
        Mockito.when(playerRepository.findByNumber(ID_VALID)).thenReturn(detail);
        appServiceImpl.deletePlayer("in-valid");
    }
    
    @Test
    public final void testDeletePlayerNull() {
        appServiceImpl.deletePlayer(null);
    }

    @Test
    public final void testGetDetailNotFound() {
        DomainException exception = new DomainException(HttpStatus.NOT_FOUND);
        Mockito.when(playerRepository.findByNumber(ID_VALID)).thenThrow(exception);
        try {
            appServiceImpl.getPlayer(ID_VALID);
            fail("should not be here");
        } catch (DomainException e) {
            HttpStatus code = e.getStatusCode();
            assertEquals(code, HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public final void testGetDetailDataAccessException() {
        DataAccessException exception = new RecoverableDataAccessException("this is a test");
        Mockito.when(playerRepository.findByNumber(ID_VALID)).thenThrow(exception);
        try {
            appServiceImpl.getPlayer(ID_VALID);
            fail("should not be here");
        } catch (DomainException e) {
            HttpStatus code = e.getStatusCode();
            assertEquals(code, HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public final void testGetDetailNull() {
        Mockito.when(playerRepository.findByNumber(ID_VALID)).thenReturn(null);
        try {
            appServiceImpl.getPlayer(ID_VALID);
            fail("should not be here");
        } catch (DomainException e) {
            HttpStatus code = e.getStatusCode();
            assertEquals(code, HttpStatus.NOT_FOUND);
        }
    }

}
