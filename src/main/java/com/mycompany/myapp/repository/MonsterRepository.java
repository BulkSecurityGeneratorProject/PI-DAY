package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Monster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Monster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long> {

}
