/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.SetupSuperherosUnitTests;
import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhero;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jswan
 */
public class OrganizationDaoTest {

    OrganizationDao organizationDao;
    LocationDao locationDao;

    public OrganizationDaoTest() {
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

        organizationDao = ctx.getBean("organizationDao", OrganizationDao.class);
        locationDao = ctx.getBean("locationDao", LocationDao.class);

        SetupSuperherosUnitTests ds = new SetupSuperherosUnitTests();

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addOrganization method, of class OrganizationDao.
     */
    @Ignore
    @Test
    public void testAddOrganization() {
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


        Organization organization = new Organization();
        organization.setOrganizationName("Avengers");
        organization.setOrganizationDescription("Wayne Manor");
        organization.setOrganizationContact("911");
        organization.setLocation(newLocal);

        organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertEquals(fromDao, organization);

    }
    /**
     * Test of deleteOrganization method, of class OrganizationDao.
     */
    @Ignore
    @Test
    public void testDeleteOrganization() {
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

        Organization organization = new Organization();
        organization.setOrganizationName("Avengers");
        organization.setOrganizationDescription("Wayne Manor");
        organization.setOrganizationContact("911");
        organization.setLocation(newLocal);

        organizationDao.addOrganization(organization);

        assertEquals(organizationDao.getAllOrganizations().size(), 1);

        organizationDao.deleteOrganization(organization.getOrganizationId());

        assertEquals(organizationDao.getAllOrganizations().size(), 0);

    }

    /**
     * Test of updateOrganization method, of class OrganizationDao.
     */
    @Test
    public void testUpdateOrganization() {
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

        Organization organization = new Organization();
        organization.setOrganizationName("Avengers");
        organization.setOrganizationDescription("Wayne Manor");
        organization.setOrganizationContact("911");
        organization.setLocation(newLocal);
        organization.setLocation(newLocal);

        organizationDao.addOrganization(organization);

        Location local2 = new Location();
        local2.setLocationName("BrookPark Urgent Care");
        local2.setLocationDescription("Urgent Care");
        local2.setAddress("15900 Snow Rd #200");
        local2.setCity("Brook Park");
        local2.setState("OH");
        local2.setZip("44142");
        local2.setLatitude(41.405279);
        local2.setLongitude(-81.809310);

        locationDao.addLocation(local2);
        organization.setLocation(local2);

        Organization fromDb = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertEquals(fromDb.getLocation(), newLocal);

        organizationDao.updateOrganization(organization);
        fromDb = organizationDao.getOrganizationById(organization.getOrganizationId());

        assertEquals(fromDb.getLocation(), local2);
    }

    /**
     * Test of getOrganizationById method, of class OrganizationDao.
     */
    @Test
    public void testGetOrganizationById() {
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

        Organization organization = new Organization();
        organization.setOrganizationName("Avengers");
        organization.setOrganizationDescription("Wayne Manor");
        organization.setOrganizationContact("911");
        organization.setLocation(newLocal);

        organizationDao.addOrganization(organization);
        Organization fromDb = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertEquals(fromDb, organization);
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDao.
     */
    @Test
    public void testGetAllOrganizations() {
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

        Organization organization = new Organization();
        List<Superhero> members = new ArrayList();
        organization.setOrganizationName("Avengers");
        organization.setOrganizationDescription("Wayne Manor");
        organization.setOrganizationContact("911");
        organization.setLocation(newLocal);
        organization.setMembers(members);
        organizationDao.addOrganization(organization);

        List<Organization> orgList = organizationDao.getAllOrganizations();
        assertTrue(orgList.size() == 1);
        assertEquals(orgList.get(0), organization);


    }

    @Test
    public void getOrganizationByLocationId() {
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

        Organization organization = new Organization();
        organization.setOrganizationName("Avengers");
        organization.setOrganizationDescription("Wayne Manor");
        organization.setOrganizationContact("911");
        organization.setLocation(newLocal);

        organizationDao.addOrganization(organization);

        Organization org = organizationDao.getOrganizationByLocationId(newLocal.getLocationId());

        assertEquals(org.getLocation().getLocationId(), newLocal.getLocationId());
    }


}
