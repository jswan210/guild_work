/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.SetupSuperherosUnitTests;
import com.sg.superherosightings.model.Sighting;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
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
public class SightingServiceTest {

    OrganizationService orgService;
    LocationService locationService;
    SuperheroService superService;
    SuperheroOrganizationService soService;
    SightingService sightingService;

    public SightingServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() throws SuperheroAlreadyExistsException,
            LocationAlreadyExistsException,
            OrganizationNameAlreadyExistsException,
            LocationExistsInOrganizationException,
            SuperheroLinkExistsInSuperOrganizationException {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        soService = ctx.getBean("superheroOrganizationService", SuperheroOrganizationService.class);
        orgService = ctx.getBean("organizationService", OrganizationService.class);
        locationService = ctx.getBean("locationService", LocationService.class);
        superService = ctx.getBean("superheroService", SuperheroService.class);
        sightingService = ctx.getBean("sightingService", SightingService.class);
        SetupSuperherosUnitTests superSetup = new SetupSuperherosUnitTests();

        locationService.addLocation(superSetup.createLocation1());
        locationService.addLocation(superSetup.createLocation2());
        locationService.addLocation(superSetup.createLocation3());

        orgService.addOrganization(superSetup.createOrganization1());
        orgService.addOrganization(superSetup.createOraganization2());

        superService.addSuperhero(superSetup.createSuperhero1());
        superService.addSuperhero(superSetup.createSuperhero2());
        superService.addSuperhero(superSetup.createSuperhero3());

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addSighting method, of class SightingService.
     */
    @Test
    public void testAddSighting() throws Exception {
        Sighting newSighting = new Sighting();

        newSighting.setDate(Timestamp.valueOf(LocalDateTime.parse("2017-12-29T00:00:00")));
        newSighting.setSuperhero(superService.getSuperheroById(superService
                .getAllSuperheros()
                .get(0)
                .getSuperheroId()));
        newSighting.setLocation(locationService.getLocationById(locationService
                .getAllLocations()
                .get(0)));
        int sightingSize = sightingService.getAllSightings().size();
        sightingService.addSighting(newSighting);
        int sightingSize2 = sightingService.getAllSightings().size();
        List<Sighting> sightList = sightingService.getAllSightings();

        assertEquals(sightingSize2, sightingSize + 1);
    }

    /**
     * Test of addSighting method, of class SightingService Fails thanks to
     * Duplicate exception...
     */
    @Test(expected = SightingAlreadyExistsException.class)
    public void testAddSightingDuplicateSighting() throws Exception {
        Sighting newSighting = new Sighting();
        newSighting.setDate(Timestamp.valueOf(LocalDateTime.parse("2017-12-29T00:00:00")));
        newSighting.setSuperhero(superService.getSuperheroById(superService
                .getAllSuperheros()
                .get(0)
                .getSuperheroId()));
        newSighting.setLocation(locationService.getLocationById(locationService
                .getAllLocations()
                .get(0)));

        sightingService.addSighting(newSighting);

        sightingService.addSighting(newSighting);

    }

    /**
     * Test of deleteSighting method, of class SightingService.
     */
    @Test
    public void testDeleteSighting() throws Exception {
        Sighting newSighting = new Sighting();
        newSighting.setDate(Timestamp.valueOf(LocalDateTime.parse("2017-12-29T00:00:00")));
        newSighting.setSuperhero(superService.getSuperheroById(superService
                .getAllSuperheros()
                .get(0)
                .getSuperheroId()));
        newSighting.setLocation(locationService.getLocationById(locationService
                .getAllLocations()
                .get(0)));

        sightingService.addSighting(newSighting);

        int sightListSize = sightingService.getAllSightings().size();

        sightingService.deleteSighting(newSighting.getSightId());

        int updateSightListSize = sightingService.getAllSightings().size();

        assertEquals(updateSightListSize, sightListSize - 1);

    }

    /**
     * Test of updateSighting method, of class SightingService.
     */
    @Test
    public void testUpdateSighting() throws Exception {
        Sighting newSighting = new Sighting();
        newSighting.setDate(Timestamp.valueOf(LocalDateTime.parse("2017-12-29T00:00:00")));
        newSighting.setSuperhero(superService.getSuperheroById(superService
                .getAllSuperheros()
                .get(0)
                .getSuperheroId()));
        newSighting.setLocation(locationService.getLocationById(locationService
                .getAllLocations()
                .get(0)));

        sightingService.addSighting(newSighting);

        newSighting.setSuperhero(superService.getAllSuperheros()
                .get(1));

        sightingService.updateSighting(newSighting);
        Sighting checkSight = sightingService.getSightingBySupLocDate(newSighting);

        assertEquals(newSighting, checkSight);
    }

    /**
     * Test of getSightingsByLocation method, of class SightingService.
     */
    @Test
    public void testGetSightingsByLocation() {

    }

    /**
     * Test of getSightingsByDate method, of class SightingService.
     */
    @Test
    public void testGetSightingsByDate() {
    }

    /**
     * Test of getSightingsBySuperhero method, of class SightingService.
     */
    @Test
    public void testGetSightingsBySuperhero() {
    }
}
