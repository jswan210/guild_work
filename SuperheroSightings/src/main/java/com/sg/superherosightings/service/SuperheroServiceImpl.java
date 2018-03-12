/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.SuperheroDao;

import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superhero;
import com.sg.superherosightings.model.SuperheroOrganizations;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author jswan
 */
public class SuperheroServiceImpl implements SuperheroService {

    private SuperheroDao superDao;
    private SightingService sightingService;
    private SuperheroOrganizationService superOrgService;

    @Inject
    public SuperheroServiceImpl(SuperheroDao superDao,
            SightingService sightingService,
            SuperheroOrganizationService superOrgService) {
        this.superDao = superDao;
        this.sightingService = sightingService;
        this.superOrgService = superOrgService;
    }

    @Override
    public Superhero addSuperhero(Superhero superhero) throws
            SuperheroAlreadyExistsException {

        boolean checkSuperName = superheroNameCheck(superhero);
        if (checkSuperName != true) {
            throw new SuperheroAlreadyExistsException("Superhero at least in name already exists");
        }

        return superDao.addSuperhero(superhero);
    }

    @Override
    public void deleteSuperhero(int superheroId) throws SuperheroLinkExistsInSightingException,
            OrganizationLinksExistsInSuperOrgException {
        List<Sighting> superSightingList = sightingService.getSightingsBySuperhero(superheroId);
        if (!superSightingList.isEmpty()) {
            throw new SuperheroLinkExistsInSightingException(" need to take care of the links"
                    + "to a sighting before you delete the superhero...");
        }

        List<SuperheroOrganizations> superOrgList = superOrgService.getSuperheroOrganizationsBySuperhero(superheroId);
        if (!superOrgList.isEmpty()) {
            throw new OrganizationLinksExistsInSuperOrgException(
                    "A link an organizaiton needs to be deleted first...");
        }
        superDao.deleteSuperhero(superheroId);
    }

    @Override
    public Superhero updateSuperhero(Superhero superhero) throws SuperheroAlreadyExistsException {
        boolean checkSuper = sperheroNameCheckForUpdate(superhero);
        if (checkSuper != true) {
            throw new SuperheroAlreadyExistsException("Superhero, at least by name already Exists");
        }
        return superDao.updateSuperhero(superhero);
    }

    @Override
    public Superhero getSuperheroById(int superheroId) {
        return superDao.getSuperheroById(superheroId);
    }

    @Override
    public List<Superhero> getAllSuperheros() {
        return superDao.getAllSuperheros();
    }
// ------ VALIDATION METHOD(S) -------

    private boolean superheroNameCheck(Superhero superhero) {
        Superhero checkSuper = superDao.getSuperheroByName(superhero);
        return checkSuper == null;
    }

    private boolean sperheroNameCheckForUpdate(Superhero superhero) {
        Superhero checkSuper = superDao.getSuperheroByName(superhero);
        return checkSuper == null || checkSuper.getSuperheroId() == superhero.getSuperheroId();
    }

}
