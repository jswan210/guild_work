/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.SetupSuperherosUnitTests;
import com.sg.superherosightings.model.Location;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jswan
 */
public class LocationServiceTest {

    LocationService locationService;

    public LocationServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws LocationAlreadyExistsException {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        locationService = ctx.getBean("locationService", LocationService.class);
        SetupSuperherosUnitTests superSetup = new SetupSuperherosUnitTests();
        locationService.addLocation(superSetup.createLocation1());
        locationService.addLocation(superSetup.createLocation2());
        locationService.addLocation(superSetup.createLocation3());

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addLocation method, of class LocationService.
     */
    //@Ignore
    @Test
    public void testAddLocation() throws Exception {
        Location newLocal = new Location();
        newLocal.setLocationName("A New Place");
        newLocal.setLocationDescription("Hospital");
        newLocal.setAddress("2000 Main St");
        newLocal.setCity("berea");
        newLocal.setState("OH");
        newLocal.setZip("44017");
        newLocal.setLatitude(42.370249);
        newLocal.setLongitude(-80.832177);

        locationService.addLocation(newLocal);
        List<Location> locationList = locationService.getAllLocations();

        Location fromDao = locationService.getLocationById(newLocal);
        assertEquals(fromDao, newLocal);
    }

    @Test(expected = LocationAlreadyExistsException.class)
    public void testAddLocationThrowsException() throws Exception {
        Location newLocal = new Location();
        newLocal.setLocationName("A New Place");
        newLocal.setLocationDescription("Hospital");
        newLocal.setAddress("2000 Main St");
        newLocal.setCity("berea");
        newLocal.setState("OH");
        newLocal.setZip("44017");
        newLocal.setLatitude(42.370249);
        newLocal.setLongitude(-80.832177);

        locationService.addLocation(newLocal);

        Location newLocalDuplicate = new Location();
        newLocalDuplicate.setLocationName("A New Place");
        newLocalDuplicate.setLocationDescription("Hospital");
        newLocalDuplicate.setAddress("2000 Main St");
        newLocalDuplicate.setCity("berea");
        newLocalDuplicate.setState("OH");
        newLocalDuplicate.setZip("44017");
        newLocalDuplicate.setLatitude(42.370249);
        newLocalDuplicate.setLongitude(-80.832177);

        locationService.addLocation(newLocalDuplicate);
    }

    /**
     * Test of deleteLocation method, of class LocationService.
     */
    @Test
    public void testDeleteLocation() throws Exception {
        List<Location> locList = locationService.getAllLocations();

        Location newLocal = new Location();
        newLocal.setLocationName("A New Place");
        newLocal.setLocationDescription("Hospital");
        newLocal.setAddress("2000 Main St");
        newLocal.setCity("berea");
        newLocal.setState("OH");
        newLocal.setZip("44017");
        newLocal.setLatitude(42.370249);
        newLocal.setLongitude(-80.832177);

        locationService.addLocation(newLocal);

        locationService.deleteLocation(newLocal);
        List<Location> locations = locationService.getAllLocations();

        assertEquals(locations.size(), locList.size());

    }

    /**
     * Test of updateLocation method, of class LocationService.
     */
    @Test
    public void testUpdateLocation() throws Exception {
        Location updateLocation = locationService.getAllLocations().get(2);

        updateLocation.setLocationDescription("A Cool PLace to go...");

        locationService.updateLocation(updateLocation);

        assertEquals(locationService.getAllLocations().get(2), updateLocation);
    }

    /**
     * Test of getLocationById method, of class LocationService.
     */
    @Test
    public void testGetLocationById() {
        Location findLocation = locationService.getAllLocations().get(2);

        Location l = locationService.getLocationById(findLocation);

        assertEquals(findLocation, l);

    }

    // No need to Check ALL i used the method in some of them above..it works...
}
