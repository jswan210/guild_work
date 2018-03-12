/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.SightingDao;
import com.sg.superherosightings.model.Sighting;
import java.sql.Timestamp;
import java.util.List;


/**
 *
 * @author jswan
 */
public class SightingServiceImpl implements SightingService {

    private SightingDao sightingDao;

    public SightingServiceImpl(SightingDao sightingDao) {
        this.sightingDao = sightingDao;
    }

    @Override
    public Sighting addSighting(Sighting sighting)
            throws SightingAlreadyExistsException,
            SuperheroTwoPlacesAtOnceException {
        boolean check = checkSightingAlreadyExists(sighting);
        if(check !=true){
            throw new SightingAlreadyExistsException(" SightingExits, no need to duplicate");
        }

        boolean checkSuperDate = checkSuperDateSimilarity(sighting);

        if (checkSuperDate != true) {
            throw new SuperheroTwoPlacesAtOnceException("Can a superhero be in two places at the exact same time?");
        }
        return sightingDao.addSighting(sighting);
    }

    private boolean checkSuperDateSimilarity(Sighting sighting) {
        Sighting checkSighting = sightingDao.getSightingBySuperDate(sighting);
        return checkSighting == null;
    }

    private boolean checkSightingAlreadyExists(Sighting sighting){
        Sighting checkSighting = sightingDao.getSightingBySuperLocDate(sighting);
        return checkSighting == null;
        
    }
    
    @Override
    public void deleteSighting(int sightId) {
        sightingDao.deleteSighting(sightId);
    }

    @Override
    public Sighting updateSighting(Sighting sighting)
            throws SightingAlreadyExistsException {
        boolean check = checkSightingAlreadyExists(sighting);
        if (check != true) {
            throw new SightingAlreadyExistsException(" SightingExits, no need to duplicate");
        }
        return sightingDao.updateSighting(sighting);
    }

    @Override
    public List<Sighting> getSightingsByLocation(int locationId) {
        return sightingDao.getSightingsByLocation(locationId);
    }

    @Override
    public List<Sighting> getSightingsByDate(Timestamp date) {
        return sightingDao.getSightingsByDate(date);
    }

    @Override
    public List<Sighting> getSightingsBySuperhero(int superheroId) {
        return sightingDao.getSightingsBySuperhero(superheroId);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return sightingDao.getAllSightings();
    }

    @Override
    public List<Sighting> getRecentTenSightings() {
        return sightingDao.getRecentTenSightings();
    }

    @Override
    public Sighting getSightingBySupLocDate(Sighting sighting) {
        return sightingDao.getSightingBySuperLocDate(sighting);
    }

    @Override
    public Sighting getSightingById(int sightId) {
        return sightingDao.getSightingById(sightId);
    }

}
