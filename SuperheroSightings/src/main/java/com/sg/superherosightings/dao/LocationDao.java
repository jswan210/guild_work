/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Location;
import java.util.List;

/**
 *
 * @author jswan
 */
public interface LocationDao {

    public Location addLocation(Location location);

    public void deleteLocation(int locationId);

    public Location updateLocation(Location location);

    public Location getLocationById(int locationId);

    public List<Location> getAllLocations();

    public Location getLocationByAddressZip(Location location);

}
