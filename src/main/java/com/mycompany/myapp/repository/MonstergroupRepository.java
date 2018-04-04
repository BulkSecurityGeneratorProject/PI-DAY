package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Monstergroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Monstergroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonstergroupRepository extends JpaRepository<Monstergroup, Long> {

}
