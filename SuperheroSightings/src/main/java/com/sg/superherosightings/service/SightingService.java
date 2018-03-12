/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.model.Sighting;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author jswan
 */
public interface SightingService {

    public Sighting addSighting(Sighting sighting) throws
            SightingAlreadyExistsException,
            SuperheroTwoPlacesAtOnceException;

    public void deleteSighting(int sightId);

    public Sighting updateSighting(Sighting sighting) throws SightingAlreadyExistsException;

    public Sighting getSightingById(int sightId);

    public Sighting getSightingBySupLocDate(Sighting sighting);

    public List<Sighting> getSightingsByLocation(int locationId);

    public List<Sighting> getSightingsByDate(Timestamp date);

    public List<Sighting> getSightingsBySuperhero(int superheroId);

    public List<Sighting> getAllSightings();

    public List<Sighting> getRecentTenSightings();

}
