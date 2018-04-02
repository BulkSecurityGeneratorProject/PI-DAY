package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Monster;
import com.mycompany.myapp.service.MonsterService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Monster.
 */
@RestController
@RequestMapping("/api")
public class MonsterResource {

    private final Logger log = LoggerFactory.getLogger(MonsterResource.class);

    private static final String ENTITY_NAME = "monster";

    private final MonsterService monsterService;

    public MonsterResource(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

    /**
     * POST  /monsters : Create a new monster.
     *
     * @param monster the monster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monster, or with status 400 (Bad Request) if the monster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monsters")
    @Timed
    public ResponseEntity<Monster> createMonster(@Valid @RequestBody Monster monster) throws URISyntaxException {
        log.debug("REST request to save Monster : {}", monster);
        if (monster.getId() != null) {
            throw new BadRequestAlertException("A new monster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Monster result = monsterService.save(monster);
        return ResponseEntity.created(new URI("/api/monsters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monsters : Updates an existing monster.
     *
     * @param monster the monster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monster,
     * or with status 400 (Bad Request) if the monster is not valid,
     * or with status 500 (Internal Server Error) if the monster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monsters")
    @Timed
    public ResponseEntity<Monster> updateMonster(@Valid @RequestBody Monster monster) throws URISyntaxException {
        log.debug("REST request to update Monster : {}", monster);
        if (monster.getId() == null) {
            return createMonster(monster);
        }
        Monster result = monsterService.save(monster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, monster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monsters : get all the monsters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of monsters in body
     */
    @GetMapping("/monsters")
    @Timed
    public List<Monster> getAllMonsters() {
        log.debug("REST request to get all Monsters");
        return monsterService.findAll();
        }

    /**
     * GET  /monsters/:id : get the "id" monster.
     *
     * @param id the id of the monster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monster, or with status 404 (Not Found)
     */
    @GetMapping("/monsters/{id}")
    @Timed
    public ResponseEntity<Monster> getMonster(@PathVariable Long id) {
        log.debug("REST request to get Monster : {}", id);
        Monster monster = monsterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(monster));
    }

    /**
     * DELETE  /monsters/:id : delete the "id" monster.
     *
     * @param id the id of the monster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monsters/{id}")
    @Timed
    public ResponseEntity<Void> deleteMonster(@PathVariable Long id) {
        log.debug("REST request to delete Monster : {}", id);
        monsterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
