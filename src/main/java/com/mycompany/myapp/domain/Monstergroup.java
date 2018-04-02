package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Monstergroup.
 */
@Entity
@Table(name = "monstergroup")
public class Monstergroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "monsters")
    private String monsters;

    @Column(name = "jhi_user")
    private String user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonsters() {
        return monsters;
    }

    public Monstergroup monsters(String monsters) {
        this.monsters = monsters;
        return this;
    }

    public void setMonsters(String monsters) {
        this.monsters = monsters;
    }

    public String getUser() {
        return user;
    }

    public Monstergroup user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
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
        Monstergroup monstergroup = (Monstergroup) o;
        if (monstergroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monstergroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Monstergroup{" +
            "id=" + getId() +
            ", monsters='" + getMonsters() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
}
