/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.LocationDao;
import com.sg.superherosightings.dao.OrganizationDao;
import com.sg.superherosightings.dao.SightingDao;
import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Sighting;
import java.util.List;

/**
 *
 * @author jswan
 */
public class LocationServiceImpl implements LocationService {

    private LocationDao locDao;
    private OrganizationDao orgDao;
    private SightingDao sightingDao;

    // @Inject
    public LocationServiceImpl(LocationDao locDao,
            OrganizationDao orgDao, SightingDao sightingDao) {
        this.locDao = locDao;
        this.orgDao = orgDao;
        this.sightingDao = sightingDao;
    }

    @Override

    public Location addLocation(Location location) throws LocationAlreadyExistsException {
        boolean check = checkDuplicate(location);
        if (check != true) {
            throw new LocationAlreadyExistsException("This location already exists,"
                    + " no need to dubplicate");
        }
        return locDao.addLocation(location);
    }

    @Override
    public void deleteLocation(Location location) throws
            LocationExistsInOrganizationException,
            LocationExistsInSightingException {
        if (checkOrgExistsInLocation(location) != true) {
            throw new LocationExistsInOrganizationException("Location Exists In Organization cannot delete");
        }
        if (checkLocationInSighting(location) != true) {
            throw new LocationExistsInSightingException("Location Exists in a Sighting and cannot be deleted");
        }

        locDao.deleteLocation(location.getLocationId());
    }

    @Override
    public Location updateLocation(Location location) throws
            LocationAlreadyExistsException {
        boolean checkLocation = checkLocationsForDuplicateEntry(location);
        if (checkLocation != true) {
            throw new LocationAlreadyExistsException("Location already Exists");
        }
        return locDao.updateLocation(location);
    }

    @Override
    public Location getLocationById(Location location) {
        return locDao.getLocationById(location.getLocationId());
    }

    @Override
    public List<Location> getAllLocations() {
        return locDao.getAllLocations();
    }

    //----------------------
    // ---checker methods--- 
    private boolean checkLocationInSighting(Location location) throws LocationExistsInSightingException {
        List<Sighting> locationInSightingList = sightingDao.getSightingsByLocation(location.getLocationId());
        return locationInSightingList.isEmpty();
    }

    private boolean checkOrgExistsInLocation(Location location) {
        Organization orgSearch = orgDao.getOrganizationByLocationId(location.getLocationId());
        return orgSearch == null;

    }

    private boolean checkDuplicate(Location location) {
        Location checkLoc = locDao.getLocationByAddressZip(location);
        return checkLoc == null;
    }

    private boolean checkLocationsForDuplicateEntry(Location location) throws
            LocationAlreadyExistsException {

        List<Location> locList = locDao.getAllLocations();
        for (Location currentloc : locList) {
            if (currentloc.getLocationId() != location.getLocationId()) {
                if (currentloc.getLocationName().equalsIgnoreCase(location.getLocationName())
                        || currentloc.getAddress().equals(location.getAddress())
                        && currentloc.getZip().equals(location.getZip())) {

                    return false;
                }
            }
        }
        return true;
    }
}
