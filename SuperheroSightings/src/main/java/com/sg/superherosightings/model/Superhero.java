/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.model;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author jswan
 */
public class Superhero {

    private int superheroId;
    private String superheroName;
    private String superheroDescription;
    private String superpower;
    private List<Organization> orgList;

    public List<Organization> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<Organization> orgList) {
        this.orgList = orgList;
    }

    public int getSuperheroId() {
        return superheroId;
    }

    public void setSuperheroId(int superheroId) {
        this.superheroId = superheroId;
    }

    public String getSuperheroName() {
        return superheroName;
    }

    public void setSuperheroName(String superheroName) {
        this.superheroName = superheroName;
    }

    public String getSuperheroDescription() {
        return superheroDescription;
    }

    public void setSuperheroDescription(String superheroDescription) {
        this.superheroDescription = superheroDescription;
    }

    public String getSuperpower() {
        return superpower;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.superheroId;
        hash = 83 * hash + Objects.hashCode(this.superheroName);
        hash = 83 * hash + Objects.hashCode(this.superheroDescription);
        hash = 83 * hash + Objects.hashCode(this.superpower);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Superhero other = (Superhero) obj;
        if (this.superheroId != other.superheroId) {
            return false;
        }
        if (!Objects.equals(this.superheroName, other.superheroName)) {
            return false;
        }
        if (!Objects.equals(this.superheroDescription, other.superheroDescription)) {
            return false;
        }
        if (!Objects.equals(this.superpower, other.superpower)) {
            return false;
        }
        return true;
    }


}
