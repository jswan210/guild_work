/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.model.Superhero;
import java.util.List;

/**
 *
 * @author jswan
 */
public interface SuperheroService {

    public Superhero addSuperhero(Superhero superhero) throws
            SuperheroAlreadyExistsException;

    public void deleteSuperhero(int superheroId) throws
            SuperheroLinkExistsInSightingException,
            OrganizationLinksExistsInSuperOrgException;

    public Superhero updateSuperhero(Superhero superhero) throws
            SuperheroAlreadyExistsException;

    public Superhero getSuperheroById(int superheroId);

    public List<Superhero> getAllSuperheros();

}
