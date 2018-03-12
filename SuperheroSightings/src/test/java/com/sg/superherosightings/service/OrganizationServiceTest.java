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
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jswan
 */
public class OrganizationServiceTest {

    OrganizationService orgService;
    LocationService locationService;

    public OrganizationServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws LocationAlreadyExistsException,
            OrganizationNameAlreadyExistsException,
            LocationExistsInOrganizationException {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        orgService = ctx.getBean("organizationService", OrganizationService.class);
        locationService = ctx.getBean("locationService", LocationService.class);

        SetupSuperherosUnitTests superSetup = new SetupSuperherosUnitTests();

        locationService.addLocation(superSetup.createLocation1());
        locationService.addLocation(superSetup.createLocation2());
        locationService.addLocation(superSetup.createLocation3());
        orgService.addOrganization(superSetup.createOrganization1());
        orgService.addOrganization(superSetup.createOraganization2());

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addOrganization method, of class OrganizationService.
     */
    @Test
    public void testAddOrganization() throws Exception {
        Location loc = locationService.getAllLocations().get(1);

        Organization organization = new Organization();
        organization.setOrganizationName("MysteryMen HQ");
        organization.setOrganizationDescription("I think it looks like a junkyard");
        organization.setOrganizationContact("Mr. Furious");
        organization.setLocation(loc);

        orgService.addOrganization(organization);

        assertEquals(organization.getLocation().getLocationId(), loc.getLocationId());
    }

    @Test(expected = OrganizationNameAlreadyExistsException.class)
    public void testAddOrganizationThrowOrgNameExistsException() throws Exception {
        Location loc = locationService.getAllLocations().get(1);
        List<Organization> orgList = orgService.getAllOrganizations();

        Organization organization = new Organization();
        organization.setOrganizationName("Avengers");
        organization.setOrganizationDescription("Wayne Manor");
        organization.setOrganizationContact("911");
        organization.setLocation(loc);

        orgService.addOrganization(organization);

        // assertEquals(organization.getLocation().getLocationId(), loc.getLocationId());
    }

    @Test(expected = LocationExistsInOrganizationException.class)
    public void testAddOrganizationThrowExistsException() throws Exception {

        Location loc = locationService.getAllLocations().get(0);

        Organization organization = new Organization();
        organization.setOrganizationName("MysteryMen HQ");
        organization.setOrganizationDescription("I think it looks like a junkyard");
        organization.setOrganizationContact("Mr. Furious");
        organization.setLocation(loc);

        orgService.addOrganization(organization);

    }

    /**
     * Test of deleteOrganization method, of class OrganizationService.
     */
    @Test
    public void testDeleteOrganization() throws Exception {
        int amountofOrgs = orgService.getAllOrganizations().size();
        int amountOfLocs = locationService.getAllLocations().size();
        Location loc = locationService.getAllLocations().get(1);

        Organization organization = new Organization();
        organization.setOrganizationName("MysteryMen HQ");
        organization.setOrganizationDescription("I think it looks like a junkyard");
        organization.setOrganizationContact("Mr. Furious");
        organization.setLocation(loc);

        orgService.addOrganization(organization);

        orgService.deleteOrganization(organization);
        assertEquals(orgService.getAllOrganizations().size(), amountofOrgs);
        assertEquals(locationService.getAllLocations().size(), amountOfLocs);

    }

    @Ignore
    @Test
    public void testDeleteOrganizationFailsDueToLinksWithSuperOrg() throws Exception {

    }

    /**
     * Test of updateOrganization method, of class OrganizationService.
     */
    @Ignore
    @Test
    public void testUpdateOrganization() {
    }

    /**
     * Test of getOrganizationById method, of class OrganizationService.
     */
    @Test
    public void testGetOrganizationById() {

        Organization org = orgService.getAllOrganizations().get(0);

        Organization orgCheck = orgService.getOrganizationById(org.getOrganizationId());
        List<Superhero> members = new ArrayList();
        orgCheck.setMembers(members);
        assertEquals(org, orgCheck);

    }
//getAllOrgs()  works--used in many methods above...

}
