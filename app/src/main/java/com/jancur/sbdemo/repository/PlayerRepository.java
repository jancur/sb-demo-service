/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jancur.sbdemo.domain.model.PlayerDetail;

/**
 * Interface for accessing the detail data store.
 */
public interface PlayerRepository extends MongoRepository<PlayerDetail, String> {

    /**
     * Get an existing detail.
     * 
     * @param number the number
     * @return the details
     */
    PlayerDetail findByNumber(String number);

    /**
     * Search all players by status.
     * 
     * @param status status to search for
     * @return list of players
     */
    List<PlayerDetail> findByStatus(String status);

}
