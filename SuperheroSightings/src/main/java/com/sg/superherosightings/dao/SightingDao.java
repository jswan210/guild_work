/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Sighting;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author jswan
 */
public interface SightingDao {

    public Sighting addSighting(Sighting sighting);

    public void deleteSighting(int sightId);

    public Sighting getSightingById(int sightId);

    public Sighting updateSighting(Sighting sighting);

    public Sighting getSightingBySuperLocDate(Sighting sighting);

    public List<Sighting> getSightingsByLocation(int locationId);

    public List<Sighting> getSightingsByDate(Timestamp date);

    public List<Sighting> getSightingsBySuperhero(int superheroId);

    public List<Sighting> getAllSightings();

    public Sighting getSightingBySuperDate(Sighting sighting);

    public List<Sighting> getRecentTenSightings();

}
