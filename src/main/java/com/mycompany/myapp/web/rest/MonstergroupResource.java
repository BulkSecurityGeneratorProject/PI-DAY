package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Monstergroup;

import com.mycompany.myapp.repository.MonstergroupRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Monstergroup.
 */
@RestController
@RequestMapping("/api")
public class MonstergroupResource {

    private final Logger log = LoggerFactory.getLogger(MonstergroupResource.class);

    private static final String ENTITY_NAME = "monstergroup";

    private final MonstergroupRepository monstergroupRepository;

    public MonstergroupResource(MonstergroupRepository monstergroupRepository) {
        this.monstergroupRepository = monstergroupRepository;
    }

    /**
     * POST  /monstergroups : Create a new monstergroup.
     *
     * @param monstergroup the monstergroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monstergroup, or with status 400 (Bad Request) if the monstergroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monstergroups")
    @Timed
    public ResponseEntity<Monstergroup> createMonstergroup(@RequestBody Monstergroup monstergroup) throws URISyntaxException {
        log.debug("REST request to save Monstergroup : {}", monstergroup);
        if (monstergroup.getId() != null) {
            throw new BadRequestAlertException("A new monstergroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Monstergroup result = monstergroupRepository.save(monstergroup);
        return ResponseEntity.created(new URI("/api/monstergroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monstergroups : Updates an existing monstergroup.
     *
     * @param monstergroup the monstergroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monstergroup,
     * or with status 400 (Bad Request) if the monstergroup is not valid,
     * or with status 500 (Internal Server Error) if the monstergroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monstergroups")
    @Timed
    public ResponseEntity<Monstergroup> updateMonstergroup(@RequestBody Monstergroup monstergroup) throws URISyntaxException {
        log.debug("REST request to update Monstergroup : {}", monstergroup);
        if (monstergroup.getId() == null) {
            return createMonstergroup(monstergroup);
        }
        Monstergroup result = monstergroupRepository.save(monstergroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, monstergroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monstergroups : get all the monstergroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of monstergroups in body
     */
    @GetMapping("/monstergroups")
    @Timed
    public List<Monstergroup> getAllMonstergroups() {
        log.debug("REST request to get all Monstergroups");
        return monstergroupRepository.findAll();
        }

    /**
     * GET  /monstergroups/:id : get the "id" monstergroup.
     *
     * @param id the id of the monstergroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monstergroup, or with status 404 (Not Found)
     */
    @GetMapping("/monstergroups/{id}")
    @Timed
    public ResponseEntity<Monstergroup> getMonstergroup(@PathVariable Long id) {
        log.debug("REST request to get Monstergroup : {}", id);
        Monstergroup monstergroup = monstergroupRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(monstergroup));
    }

    /**
     * DELETE  /monstergroups/:id : delete the "id" monstergroup.
     *
     * @param id the id of the monstergroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monstergroups/{id}")
    @Timed
    public ResponseEntity<Void> deleteMonstergroup(@PathVariable Long id) {
        log.debug("REST request to delete Monstergroup : {}", id);
        monstergroupRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
