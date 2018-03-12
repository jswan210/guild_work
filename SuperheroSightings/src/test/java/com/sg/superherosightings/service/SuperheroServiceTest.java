/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.SetupSuperherosUnitTests;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhero;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jswan
 */
public class SuperheroServiceTest {

    OrganizationService orgService;
    LocationService locationService;
    SuperheroService superService;

    public SuperheroServiceTest() {
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
        orgService = ctx.getBean("organizationService", OrganizationService.class);
        locationService = ctx.getBean("locationService", LocationService.class);
        superService = ctx.getBean("superheroService", SuperheroService.class);

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
     * Test of addSuperhero method, of class SuperheroService.
     */
    @Test
    public void testAddSuperhero() throws Exception {//SuperheroAlreadyExistsException, SuperheroLinkExistsInSuperOrganizationException {

        Superhero newSuper = new Superhero();
        
        newSuper.setSuperheroName("A CRAZY HERO");
        newSuper.setSuperheroDescription("I think hes crazy");
        newSuper.setSuperpower("is crazy a superpower?");
        
        superService.addSuperhero(newSuper);
    }

    @Test(expected = SuperheroAlreadyExistsException.class)
    public void testAddSuperheroFailDuplicateHero() throws Exception {
        Superhero newSuper = new Superhero();

        newSuper.setSuperheroName("A CRAZY HERO");
        newSuper.setSuperheroDescription("I think hes crazy");
        newSuper.setSuperpower("is crazy a superpower?");
        superService.addSuperhero(newSuper);


        Superhero duplicateSuper = new Superhero();

        duplicateSuper.setSuperheroName("A CRAZY HERO");
        duplicateSuper.setSuperheroDescription("I think hes crazy");
        duplicateSuper.setSuperpower("is crazy a superpower?");
        superService.addSuperhero(duplicateSuper);

    }

    @Test
    public void testGetAllOrgsAssociatedWithSuperhero() {

        //  superService.g

    }
    /**
     * Test of deleteSuperhero method, of class SuperheroService.
     */
    @Test
    public void testDeleteSuperhero() {

    }

    /**
     * Test of updateSuperhero method, of class SuperheroService.
     */
    @Test
    public void testUpdateSuperhero() {
    }

    /**
     * Test of getSuperheroById method, of class SuperheroService.
     */
    @Test
    public void testGetSuperheroById() {
    }

    /**
     * Test of getAllSuperheros method, of class SuperheroService.
     */
    @Test
    public void testGetAllSuperheros() {
    }

}
