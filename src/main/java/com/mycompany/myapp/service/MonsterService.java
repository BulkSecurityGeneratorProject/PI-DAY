package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Monster;
import java.util.List;

/**
 * Service Interface for managing Monster.
 */
public interface MonsterService {

    /**
     * Save a monster.
     *
     * @param monster the entity to save
     * @return the persisted entity
     */
    Monster save(Monster monster);

    /**
     * Get all the monsters.
     *
     * @return the list of entities
     */
    List<Monster> findAll();

    /**
     * Get the "id" monster.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Monster findOne(Long id);

    /**
     * Delete the "id" monster.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
