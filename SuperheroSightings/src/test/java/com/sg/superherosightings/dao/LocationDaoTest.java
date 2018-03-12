/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.SetupSuperherosUnitTests;
import com.sg.superherosightings.model.Location;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jswan
 */
public class LocationDaoTest {
    SightingDao sightingDao;
    OrganizationDao organizationDao;
    LocationDao locationDao;


    public LocationDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        sightingDao = ctx.getBean("sightingDao", SightingDao.class);
        organizationDao = ctx.getBean("organizationDao", OrganizationDao.class);
        locationDao = ctx.getBean("locationDao", LocationDao.class);

        SetupSuperherosUnitTests ds = new SetupSuperherosUnitTests();

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addLocation method, of class LocationDao.
     */
    @Test
    public void testAddLocation() {
        Location newLocal = new Location();
        newLocal.setLocationName("SouthWest General");
        newLocal.setLocationDescription("Hospital");
        newLocal.setAddress("18697 Bagley Rd");
        newLocal.setCity("Middleburg Heights");
        newLocal.setState("OH");
        newLocal.setZip("44130");
        newLocal.setLatitude(41.370249);
        newLocal.setLongitude(-81.832177);

        locationDao.addLocation(newLocal);

        Location fromDao = locationDao.getLocationById(newLocal.getLocationId());
        assertEquals(fromDao, newLocal);

    }

    /**
     * Test of updateLocation method, of class LocationDao.
     */
    @Test
    public void testUpdateLocation() {

        Location newLocal = new Location();
        newLocal.setLocationName("SouthWest General");
        newLocal.setLocationDescription("Hospital");
        newLocal.setAddress("18697 Bagley Rd");
        newLocal.setCity("Middleburg Heights");
        newLocal.setState("OH");
        newLocal.setZip("44130");
        newLocal.setLatitude(41.370249);
        newLocal.setLongitude(-81.832177);

        locationDao.addLocation(newLocal);
        Location fromDb = locationDao.getLocationById(newLocal.getLocationId());
        
        fromDb.setAddress("1025 Baker Rd");

        locationDao.updateLocation(fromDb);

        assertEquals(fromDb.getAddress(), "1025 Baker Rd");

    }

    /**
     * Test of getLocationById method, of class LocationDao.
     */
    @Test
    public void testGetLocationById() {
        Location newLocal = new Location();
        newLocal.setLocationName("SouthWest General");
        newLocal.setLocationDescription("Hospital");
        newLocal.setAddress("18697 Bagley Rd");
        newLocal.setCity("Middleburg Heights");
        newLocal.setState("OH");
        newLocal.setZip("44130");
        newLocal.setLatitude(41.370249);
        newLocal.setLongitude(-81.832177);

        locationDao.addLocation(newLocal);

        Location newLocal2 = new Location();
        newLocal2.setLocationName("Single family home");
        newLocal2.setLocationDescription("Suburban home");
        newLocal2.setAddress("193 S. Rocky River Dr");
        newLocal2.setCity("Berea");
        newLocal2.setState("OH");
        newLocal2.setZip("44017");
        newLocal2.setLatitude(41.361162);
        newLocal2.setLongitude(-81.852682);

        locationDao.addLocation(newLocal2);

        List<Location> lList = locationDao.getAllLocations();
        Location ourLocation = lList.get(1);

        Location fromDb = locationDao.getLocationById(ourLocation.getLocationId());
        assertEquals(fromDb.getAddress(), "193 S. Rocky River Dr");
    }

    @Test
    public void testDeleteLocation() {
        Location newLocal = new Location();
        newLocal.setLocationName("SouthWest General");
        newLocal.setLocationDescription("Hospital");
        newLocal.setAddress("18697 Bagley Rd");
        newLocal.setCity("Middleburg Heights");
        newLocal.setState("OH");
        newLocal.setZip("44130");
        newLocal.setLatitude(41.370249);
        newLocal.setLongitude(-81.832177);

        locationDao.addLocation(newLocal);

        Location newLocal2 = new Location();
        newLocal2.setLocationName("A New Place");
        newLocal2.setLocationDescription("Hospital");
        newLocal2.setAddress("2000 Main St");
        newLocal2.setCity("berea");
        newLocal2.setState("OH");
        newLocal2.setZip("44017");
        newLocal2.setLatitude(42.370249);
        newLocal2.setLongitude(-80.832177);

        locationDao.addLocation(newLocal2);

        List<Location> locationList1 = locationDao.getAllLocations();

        locationDao.deleteLocation(newLocal2.getLocationId());
        List<Location> afterDelete = locationDao.getAllLocations();

        assertTrue(locationList1.size() != afterDelete.size());


    }

    /**
     * Test of getAllLocations method, of class LocationDao.
     */
    @Test
    public void testGetAllLocations() {
        Location newLocal = new Location();
        newLocal.setLocationName("SouthWest General");
        newLocal.setLocationDescription("Hospital");
        newLocal.setAddress("18697 Bagley Rd");
        newLocal.setCity("Middleburg Heights");
        newLocal.setState("OH");
        newLocal.setZip("44130");
        newLocal.setLatitude(41.370249);
        newLocal.setLongitude(-81.832177);

        locationDao.addLocation(newLocal);

        Location newLocal2 = new Location();
        newLocal2.setLocationName("Single family home");
        newLocal2.setLocationDescription("Suburban home");
        newLocal2.setAddress("193 S. Rocky River Dr");
        newLocal2.setCity("Berea");
        newLocal2.setState("OH");
        newLocal2.setZip("44017");
        newLocal2.setLatitude(41.361162);
        newLocal2.setLongitude(-81.852682);

        locationDao.addLocation(newLocal2);

        List<Location> lList = locationDao.getAllLocations();

        assertTrue(lList.size() == 2);

    }

    @Test
    public void testGetLocationByAddressAndZip() {

        Location newLocal = new Location();
        newLocal.setLocationName("SouthWest General");
        newLocal.setLocationDescription("Hospital");
        newLocal.setAddress("18697 Bagley Rd");
        newLocal.setCity("Middleburg Heights");
        newLocal.setState("OH");
        newLocal.setZip("44130");
        newLocal.setLatitude(41.370249);
        newLocal.setLongitude(-81.832177);

        locationDao.addLocation(newLocal);

        Location newLocal2 = new Location();
        newLocal2.setLocationName("Single family home");
        newLocal2.setLocationDescription("Suburban home");
        newLocal2.setAddress("193 S. Rocky River Dr");
        newLocal2.setCity("Berea");
        newLocal2.setState("OH");
        newLocal2.setZip("44017");
        newLocal2.setLatitude(41.361162);
        newLocal2.setLongitude(-81.852682);

        locationDao.addLocation(newLocal2);
        Location location3 = new Location();
        location3.setAddress("18697 Bagley Rd");
        location3.setZip("44130");

        Location location4 = locationDao.getLocationByAddressZip(location3);

        assertEquals(location4, newLocal);

    }
}
