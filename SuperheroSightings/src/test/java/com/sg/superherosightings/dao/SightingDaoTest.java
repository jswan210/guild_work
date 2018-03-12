/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.SetupSuperherosUnitTests;
import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superhero;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
public class SightingDaoTest {
    SightingDao sightingDao;
    LocationDao locationDao;
    SuperheroDao superDao;
    OrganizationDao organizationDao;

    public SightingDaoTest() {
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

        locationDao = ctx.getBean("locationDao", LocationDao.class);
        superDao = ctx.getBean("superheroDao", SuperheroDao.class);
        sightingDao = ctx.getBean("sightingDao", SightingDao.class);
        organizationDao = ctx.getBean("organizationDao", OrganizationDao.class);

        SetupSuperherosUnitTests superSetup = new SetupSuperherosUnitTests();

        Location local = locationDao.addLocation(superSetup.createLocation1());
        local = locationDao.addLocation(superSetup.createLocation2());

        Superhero super1 = superDao.addSuperhero(superSetup.createSuperhero1());
        super1 = superDao.addSuperhero(superSetup.createSuperhero2());

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addSighting method, of class SightingDao.
     */
    @Test
    public void testAddSighting() {
        Timestamp myDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String stringDate = "2007-12-03";
        try {
            myDate = Timestamp.valueOf(LocalDateTime.parse(stringDate, formatter));
        } catch (DateTimeParseException ex) {
            stringDate += "T00:00:00";
            myDate = Timestamp.valueOf(LocalDateTime.parse(stringDate, formatter));
        }

        List<Location> locationList = locationDao.getAllLocations();
        List<Superhero> superList = superDao.getAllSuperheros();

        Sighting sighting = new Sighting();
        sighting.setSuperhero(superList.get(0));
        sighting.setLocation(locationList.get(0));
        sighting.setDate(myDate);

        sightingDao.addSighting(sighting);

        Sighting fromDb = sightingDao.getAllSightings().get(0);
        fromDb.setLocation(locationDao.getLocationById(fromDb.getLocation().getLocationId()));
        fromDb.setSuperhero(superDao.getSuperheroById(fromDb.getSuperhero().getSuperheroId()));

        assertEquals(fromDb, sighting);

    }

    /**
     * Test of deleteSighting method, of class SightingDao.
     */
    @Test
    public void testDeleteSighting() {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String stringDate = "2007-12-03T00:00:00";

        List<Location> locationList = locationDao.getAllLocations();
        List<Superhero> superList = superDao.getAllSuperheros();

        Sighting sighting = new Sighting();
        sighting.setSuperhero(superList.get(0));
        sighting.setLocation(locationList.get(0));
        sighting.setDate(Timestamp.valueOf(LocalDateTime.parse(stringDate, formatter)));

        sightingDao.addSighting(sighting);

        sightingDao.deleteSighting(sighting.getSightId());

        assertEquals(sightingDao.getAllSightings().size(), 0);

    }

    /**
     * Test of updateSighting method, of class SightingDao.
     */
    @Test
    public void testUpdateSighting() {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String stringDate = "2007-12-03T00:00:00";

        List<Location> locationList = locationDao.getAllLocations();
        List<Superhero> superList = superDao.getAllSuperheros();

        Sighting sighting = new Sighting();
        sighting.setSuperhero(superList.get(0));
        sighting.setLocation(locationList.get(0));
        sighting.setDate(Timestamp.valueOf(LocalDateTime.parse(stringDate, formatter)));

        sightingDao.addSighting(sighting);
        
        sighting.setSuperhero(superList.get(1));
        
        sightingDao.updateSighting(sighting);
        
        assertEquals(sighting.getSuperhero(), superList.get(1));
    }

    /**
     * Test of getSightingsByLocation method, of class SightingDao.
     */
    @Test
    public void testGetSightingsByLocation() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String stringDate = "2007-12-03T00:00:00";

        List<Location> locationList = locationDao.getAllLocations();
        List<Superhero> superList = superDao.getAllSuperheros();

        Sighting sighting = new Sighting();
        sighting.setSuperhero(superList.get(0));
        sighting.setLocation(locationList.get(0));
        sighting.setDate(Timestamp.valueOf(LocalDateTime.parse(stringDate, formatter)));

        sightingDao.addSighting(sighting);

        stringDate = "2007-12-04T00:00:00";

        Sighting sighting2 = new Sighting();
        sighting2.setSuperhero(superList.get(1));
        sighting2.setLocation(locationList.get(1));
        sighting2.setDate(Timestamp.valueOf(LocalDateTime.parse(stringDate, formatter)));

        sightingDao.addSighting(sighting2);

        Location ourLocation = locationDao.getAllLocations().get(1);

        List<Sighting> ourSighting = sightingDao.getSightingsByLocation(ourLocation.getLocationId());

        assertEquals(ourLocation.getLocationId(), ourSighting.get(0).getLocation().getLocationId());

    }

    /**
     * Test of getSightingsByDate method, of class SightingDao.
     */
    @Test
    public void testGetSightingsByDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String stringDate = "2007-12-03T00:00:00";
        Timestamp ourDate = Timestamp.valueOf(LocalDateTime.parse(stringDate, formatter));

        
        List<Location> locationList = locationDao.getAllLocations();
        List<Superhero> superList = superDao.getAllSuperheros();

        Sighting sighting = new Sighting();
        sighting.setSuperhero(superList.get(0));
        sighting.setLocation(locationList.get(0));
        sighting.setDate(Timestamp.valueOf(LocalDateTime.parse(stringDate, formatter)));

        sightingDao.addSighting(sighting);

        List<Sighting> ourSighting = sightingDao.getSightingsByDate(ourDate);
        assertEquals(sighting.getDate(), ourSighting.get(0).getDate());
    }

    /**
     * Test of getSightingsBySuperhero method, of class SightingDao.
     */
    @Test
    public void testGetSightingsBySuperhero() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String stringDate = "2007-12-03T00:00:00";

        List<Location> locationList = locationDao.getAllLocations();
        List<Superhero> superList = superDao.getAllSuperheros();

        Sighting sighting = new Sighting();
        sighting.setSuperhero(superList.get(0));
        sighting.setLocation(locationList.get(0));
        sighting.setDate(Timestamp.valueOf(LocalDateTime.parse(stringDate, formatter)));

        sightingDao.addSighting(sighting);

        stringDate = "2007-12-04T00:00:00";

        Sighting sighting2 = new Sighting();
        sighting2.setSuperhero(superList.get(1));
        sighting2.setLocation(locationList.get(1));
        sighting2.setDate(Timestamp.valueOf(LocalDateTime.parse(stringDate, formatter)));

        sightingDao.addSighting(sighting2);

        Superhero ourSuperhero = superDao.getAllSuperheros().get(1);

        List<Sighting> ourSighting = sightingDao.getSightingsBySuperhero(ourSuperhero.getSuperheroId());

        assertEquals(ourSuperhero.getSuperheroId(), ourSighting.get(0).getSuperhero().getSuperheroId());

    }

    /**
     * Test of getAllSightings method, of class SightingDao.
     */
    @Test
    public void testGetAllSightings() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String stringDate = "2007-12-03T00:00:00";
        Timestamp ourDate = Timestamp.valueOf(LocalDateTime.parse(stringDate, formatter));

        List<Location> locationList = locationDao.getAllLocations();
        List<Superhero> superList = superDao.getAllSuperheros();

        Sighting sighting = new Sighting();
        sighting.setSuperhero(superList.get(0));
        sighting.setLocation(locationList.get(0));
        sighting.setDate(Timestamp.valueOf(LocalDateTime.parse(stringDate, formatter)));

        sightingDao.addSighting(sighting);

        assertEquals(sightingDao.getAllSightings().size(), 1);

    }


}
