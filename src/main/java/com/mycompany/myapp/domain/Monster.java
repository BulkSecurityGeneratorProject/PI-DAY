package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Monster.
 */
@Entity
@Table(name = "monster")
public class Monster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "page_no")
    private Integer pageNo;

    @NotNull
    @Column(name = "monster_id", nullable = false)
    private Integer monsterID;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Monster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Monster pageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getMonsterID() {
        return monsterID;
    }

    public Monster monsterID(Integer monsterID) {
        this.monsterID = monsterID;
        return this;
    }

    public void setMonsterID(Integer monsterID) {
        this.monsterID = monsterID;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Monster monster = (Monster) o;
        if (monster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Monster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", pageNo=" + getPageNo() +
            ", monsterID=" + getMonsterID() +
            "}";
    }
}
