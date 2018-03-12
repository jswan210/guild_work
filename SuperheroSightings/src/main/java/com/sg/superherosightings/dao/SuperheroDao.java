/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhero;
import java.util.List;

/**
 *
 * @author jswan
 */
public interface SuperheroDao {

    public Superhero addSuperhero(Superhero superhero);

    public void deleteSuperhero(int superheroId);

    public Superhero updateSuperhero(Superhero superhero);

    public Superhero getSuperheroById(int superheroId);

    public Superhero getSuperheroByName(Superhero superhero);

    public List<Superhero> getAllSuperheros();

    public List<Organization> getAllOrgsAssociatedWithSuperhero(int superheroId);
}
