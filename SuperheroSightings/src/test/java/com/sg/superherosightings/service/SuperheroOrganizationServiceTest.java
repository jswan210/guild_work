/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.SetupSuperherosUnitTests;
import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhero;
import com.sg.superherosightings.model.SuperheroOrganizations;
import java.util.List;
import org.junit.After;
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
public class SuperheroOrganizationServiceTest {

    OrganizationService orgService;
    LocationService locationService;
    SuperheroService superService;
    SuperheroOrganizationService soService;

    public SuperheroOrganizationServiceTest() {
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

        SetupSuperherosUnitTests superSetup = new SetupSuperherosUnitTests();

        locationService.addLocation(superSetup.createLocation1());
        locationService.addLocation(superSetup.createLocation2());
        locationService.addLocation(superSetup.createLocation3());

        superService.addSuperhero(superSetup.createSuperhero2());


        soService.addSuperheroOrganizations(superService.addSuperhero(superSetup.createSuperhero1()),
                orgService.addOrganization(superSetup.createOrganization1()));
        soService.addSuperheroOrganizations(superService.addSuperhero(superSetup.createSuperhero3()),
                orgService.addOrganization(superSetup.createOraganization2()));
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addSuperheroOrganizations method, of class SuperheroOrganizationService.
     */
    @Test
    public void testAddSuperheroOrganizations() throws Exception {
     
        int superOrgSize = soService.getAllSuperheroOrganizations().size();
        
        Location location = locationService.getAllLocations().get(1);
        Organization newOrg = new Organization();
        newOrg.setOrganizationName("The Best ORG-EVER");
        newOrg.setOrganizationDescription("its in the name, simply the best...");
        newOrg.setOrganizationContact("SomeGuy");
        newOrg.setLocation(location);
        orgService.addOrganization(newOrg);

        Superhero newSuper = new Superhero();
        newSuper.setSuperheroName("Master Hero");
        newSuper.setSuperheroDescription("Master...hero...");
        newSuper.setSuperpower("Kung fu...probably...");

        superService.addSuperhero(newSuper);

        soService.addSuperheroOrganizations(newSuper, newOrg);
        int superOrgSize2 = soService.getAllSuperheroOrganizations().size();
        assertEquals(superOrgSize2, superOrgSize + 1);

    }

    /**
     * Test of addSuperheroOrganizations method, of class
     * SuperheroOrganizationService. Fails though when duplicate Superhero is
     * being attached to an Organization
     */

    @Test(expected = SuperheroLinkExistsInSuperOrganizationException.class)
    public void testAddSuperheroOrganizationsFailsDuplicate() throws Exception {
        List<Superhero> superList = superService.getAllSuperheros();
        List<Organization> orgList = orgService.getAllOrganizations();

        soService.addSuperheroOrganizations(superList.get(0), orgList.get(0));
        soService.addSuperheroOrganizations(superList.get(0), orgList.get(0));
    }

    /**
     * Test of deleteSuperheroOrganizations method, of class SuperheroOrganizationService.
     */
    @Test
    public void testDeleteSuperheroOrganizations() throws Exception {

        int numberOfSuperOrgs = soService.getAllSuperheroOrganizations().size();
        soService.deleteSuperheroOrganizations(soService.getAllSuperheroOrganizations().get(0));

        int numberOfSuperOrgs2 = soService.getAllSuperheroOrganizations().size();
        assertEquals(numberOfSuperOrgs, numberOfSuperOrgs2 + 1);
    }

    /**
     * Test of getSuperheroOrganizationByOrganization method, of class SuperheroOrganizationService.
     */
    @Test
    public void testGetSuperheroOrganizationByOrganization() throws Exception {
        List<Superhero> superList = superService.getAllSuperheros();
        List<Organization> orgList = orgService.getAllOrganizations();

        List<SuperheroOrganizations> listBySuperID = soService
                .getSuperheroOrganizationsBySuperhero(superList.get(1).getSuperheroId());

        List<SuperheroOrganizations> listByOrg = soService.getSuperheroOrganizationByOrganization(listBySuperID.get(0).getOrganization().getOrganizationId());

        assertEquals(listBySuperID.get(0).getOrganization().getOrganizationName(), listByOrg.get(0).getOrganization().getOrganizationName());
    }

    /**
     * Test of getSuperheroOrganizationsBySuperhero method, of class SuperheroOrganizationService.
     */
    //  @Ignore
    @Test
    public void testGetSuperheroOrganizationsBySuperhero() throws Exception {

        List<Superhero> superList = superService.getAllSuperheros();
        List<Organization> orgList = orgService.getAllOrganizations();

        List<SuperheroOrganizations> allSuperOrgList = soService.getAllSuperheroOrganizations();
        Superhero superHero = allSuperOrgList.get(0).getSuperhero();
        Organization org = allSuperOrgList.get(0).getOrganization();


        List<SuperheroOrganizations> listBySuperID = soService
                .getSuperheroOrganizationsBySuperhero(superList.get(1).getSuperheroId());

        List<SuperheroOrganizations> listByOrg = soService.getSuperheroOrganizationByOrganization(listBySuperID.get(0).getOrganization().getOrganizationId());

        assertEquals(listBySuperID.get(0).getOrganization().getOrganizationName(), listByOrg.get(0).getOrganization().getOrganizationName());
    }

}
