package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Environ;

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

    @Column(name = "challenge")
    private Float challenge;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_size")
    private String size;

    @Enumerated(EnumType.STRING)
    @Column(name = "environment")
    private Environ environment;

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

    public Float getChallenge() {
        return challenge;
    }

    public Monster challenge(Float challenge) {
        this.challenge = challenge;
        return this;
    }

    public void setChallenge(Float challenge) {
        this.challenge = challenge;
    }

    public String getDescription() {
        return description;
    }

    public Monster description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public Monster size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Environ getEnvironment() {
        return environment;
    }

    public Monster environment(Environ environment) {
        this.environment = environment;
        return this;
    }

    public void setEnvironment(Environ environment) {
        this.environment = environment;
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
            ", challenge=" + getChallenge() +
            ", description='" + getDescription() + "'" +
            ", size='" + getSize() + "'" +
            ", environment='" + getEnvironment() + "'" +
            "}";
    }
}
