/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.SetupSuperherosUnitTests;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhero;
import com.sg.superherosightings.model.SuperheroOrganizations;
import com.sg.superherosightings.service.DuplicateSuperheroOrganizationException;
import com.sg.superherosightings.service.LocationExistsInOrganizationException;
import com.sg.superherosightings.service.OrganizationNameAlreadyExistsException;
import com.sg.superherosightings.service.OrganizationService;
import com.sg.superherosightings.service.SuperheroAlreadyExistsException;
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
public class SuperheroOrganizationsTest {

    SuperheroOrganizationsDao superOrgDao;
    SuperheroDao superDao;
    OrganizationDao orgDao;
    LocationDao locDao;
    OrganizationService orgService;
    public SuperheroOrganizationsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SuperheroAlreadyExistsException,
            OrganizationNameAlreadyExistsException,
            LocationExistsInOrganizationException {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        superOrgDao = ctx.getBean("superOrgDao", SuperheroOrganizationsDao.class);
        superDao = ctx.getBean("superheroDao", SuperheroDao.class);
        orgDao = ctx.getBean("organizationDao", OrganizationDao.class);
        orgService = ctx.getBean("organizationService", OrganizationService.class);
        locDao = ctx.getBean("locationDao", LocationDao.class);
        SetupSuperherosUnitTests superSetup = new SetupSuperherosUnitTests();

        superDao.addSuperhero(superSetup.createSuperhero1());
        superDao.addSuperhero(superSetup.createSuperhero2());
        superDao.addSuperhero(superSetup.createSuperhero3());

        locDao.addLocation(superSetup.createLocation1());
        locDao.addLocation(superSetup.createLocation2());
        locDao.addLocation(superSetup.createLocation3());

        orgService.addOrganization(superSetup.createOrganization1());
        orgService.addOrganization(superSetup.createOraganization2());

    }

    @After
    public void tearDown() {
    }


    /**
     * Test of addSuperheroOrganizations method, of class SuperheroOrganizationsDaoJdbcTemplateImpl.
     */
    @Test
    public void testAddSuperheroOrganizations() throws DuplicateSuperheroOrganizationException {
        List<Superhero> heroList = superDao.getAllSuperheros();
        List<Organization> orgList = orgDao.getAllOrganizations();
        SuperheroOrganizations newSuperOrg = new SuperheroOrganizations();
        newSuperOrg.setSuperhero(heroList.get(0));
        newSuperOrg.setOrganziation(orgList.get(0));
        superOrgDao.addSuperheroOrganizations(newSuperOrg);//, newSuperOrg.getOrganization());

        List<SuperheroOrganizations> superOrgList = superOrgDao.getAllSuperheroOrganizations();

        assertEquals(superOrgList.size(), 1);

    }

    /**
     * Test of deleteSuperheroOrganizations method, of class SuperheroOrganizationsDaoJdbcTemplateImpl.
     */
    @Test
    public void testDeleteSuperheroOrganizations() throws DuplicateSuperheroOrganizationException {
        List<Superhero> heroList = superDao.getAllSuperheros();
        List<Organization> orgList = orgDao.getAllOrganizations();
        SuperheroOrganizations newSuperOrg = new SuperheroOrganizations();
        newSuperOrg.setSuperhero(heroList.get(0));
        newSuperOrg.setOrganziation(orgList.get(0));

        superOrgDao.addSuperheroOrganizations(newSuperOrg); //.getSuperhero(), newSuperOrg.getOrganization());

        superOrgDao.deleteSuperheroOrganizations(newSuperOrg);
        List<SuperheroOrganizations> superOrgList
                = superOrgDao.getAllSuperheroOrganizations();
        assertEquals(superOrgList.size(), 0);

    }

//    /**
//     * Test of getSuperheroOrganizationByOrganization method, of class SuperheroOrganizationsDaoJdbcTemplateImpl.
//     */
    @Test
    public void testGetSuperheroOrganizationByOrganization() throws DuplicateSuperheroOrganizationException {
        List<Superhero> heroList = superDao.getAllSuperheros();
        List<Organization> orgList = orgDao.getAllOrganizations();
        SuperheroOrganizations newSuperOrg1 = new SuperheroOrganizations();
        newSuperOrg1.setSuperhero(heroList.get(0));
        newSuperOrg1.setOrganziation(orgList.get(0));

        superOrgDao.addSuperheroOrganizations(newSuperOrg1);//.getSuperhero(), newSuperOrg1.getOrganization());

        SuperheroOrganizations newSuperOrg2 = new SuperheroOrganizations();
        newSuperOrg2.setSuperhero(heroList.get(1));
        newSuperOrg2.setOrganziation(orgList.get(0));

        superOrgDao.addSuperheroOrganizations(newSuperOrg2);//.getSuperhero(), newSuperOrg2.getOrganization());

        SuperheroOrganizations newSuperOrg3 = new SuperheroOrganizations();
        newSuperOrg3.setSuperhero(heroList.get(2));
        newSuperOrg3.setOrganziation(orgList.get(1));

        superOrgDao.addSuperheroOrganizations(newSuperOrg3);//.getSuperhero(), newSuperOrg3.getOrganization());

        List<SuperheroOrganizations> superOrgFullList = superOrgDao.getAllSuperheroOrganizations();
        assertEquals(superOrgFullList.size(), 3);

        List<SuperheroOrganizations> orgsOfSuperheroList = superOrgDao.getSuperheroOrganizationByOrganization(orgList.get(0).getOrganizationId());

        assertEquals(orgsOfSuperheroList.size(), 2);
    }

    /**
     * Test of getSuperheroOrganizationsBySuperhero method, of class
     * SuperheroOrganizationsDaoJdbcTemplateImpl.
     */
    // @Ignore("not ready yet")
    @Test
    public void testGetSuperheroOrganizationsBySuperhero() throws DuplicateSuperheroOrganizationException {
        List<Superhero> heroList = superDao.getAllSuperheros();
        List<Organization> orgList = orgDao.getAllOrganizations();
        SuperheroOrganizations newSuperOrg1 = new SuperheroOrganizations();
        newSuperOrg1.setSuperhero(heroList.get(0));
        newSuperOrg1.setOrganziation(orgList.get(0));

        superOrgDao.addSuperheroOrganizations(newSuperOrg1);//.getSuperhero(), newSuperOrg1.getOrganization());

        SuperheroOrganizations newSuperOrg2 = new SuperheroOrganizations();
        newSuperOrg2.setSuperhero(heroList.get(0));
        newSuperOrg2.setOrganziation(orgList.get(1));

        superOrgDao.addSuperheroOrganizations(newSuperOrg2);//.getSuperhero(), newSuperOrg2.getOrganization());

        SuperheroOrganizations newSuperOrg3 = new SuperheroOrganizations();
        newSuperOrg3.setSuperhero(heroList.get(2));
        newSuperOrg3.setOrganziation(orgList.get(1));

        superOrgDao.addSuperheroOrganizations(newSuperOrg3);//.getSuperhero(), newSuperOrg3.getOrganization());

        List<SuperheroOrganizations> superOrgFullList = superOrgDao.getAllSuperheroOrganizations();
        assertEquals(superOrgFullList.size(), 3);

        List<SuperheroOrganizations> herosOrgsList = superOrgDao
                .getSuperheroOrganizationsBySuperhero(heroList.get(0).getSuperheroId());

        assertEquals(herosOrgsList.size(), 2);
        assertEquals(herosOrgsList.get(0).getSuperhero().getSuperheroId(), heroList.get(0).getSuperheroId());
        assertEquals(herosOrgsList.get(1).getSuperhero().getSuperheroId(), heroList.get(0).getSuperheroId());
    }

    /**
     * Test of getAllSuperheroOrganizations method, of class
     * SuperheroOrganizationsDaoJdbcTemplateImpl.
     */
//    //@Ignore("not ready yet")
    @Test
    public void testGetAllSuperheroOrganizations() throws DuplicateSuperheroOrganizationException {

        List<Superhero> heroList = superDao.getAllSuperheros();
        List<Organization> orgList = orgDao.getAllOrganizations();
        SuperheroOrganizations newSuperOrg1 = new SuperheroOrganizations();
        newSuperOrg1.setSuperhero(heroList.get(0));
        newSuperOrg1.setOrganziation(orgList.get(0));

        superOrgDao.addSuperheroOrganizations(newSuperOrg1);//.getSuperhero(), newSuperOrg1.getOrganization());

        SuperheroOrganizations newSuperOrg2 = new SuperheroOrganizations();
        newSuperOrg2.setSuperhero(heroList.get(0));
        newSuperOrg2.setOrganziation(orgList.get(1));

        superOrgDao.addSuperheroOrganizations(newSuperOrg2);//.getSuperhero(), newSuperOrg2.getOrganization());

        SuperheroOrganizations newSuperOrg3 = new SuperheroOrganizations();
        newSuperOrg3.setSuperhero(heroList.get(2));
        newSuperOrg3.setOrganziation(orgList.get(1));

        superOrgDao.addSuperheroOrganizations(newSuperOrg3);//.getSuperhero(), newSuperOrg3.getOrganization());

        List<SuperheroOrganizations> superOrgFullList = superOrgDao.getAllSuperheroOrganizations();
        assertEquals(superOrgFullList.size(), 3);
    }

}
