/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.model.Location;
import java.util.List;

/**
 *
 * @author jswan
 */
public interface LocationService {

    public Location addLocation(Location location) throws
            LocationAlreadyExistsException;

    public void deleteLocation(Location location) throws
            LocationExistsInOrganizationException,
            LocationExistsInSightingException;

    public Location updateLocation(Location location) throws
            LocationAlreadyExistsException;

    public Location getLocationById(Location location);

    public List<Location> getAllLocations();


}
