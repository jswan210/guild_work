/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings;

import com.sg.superherosightings.dao.LocationDao;
import com.sg.superherosightings.dao.OrganizationDao;
import com.sg.superherosightings.dao.SightingDao;
import com.sg.superherosightings.dao.SuperheroDao;
import com.sg.superherosightings.dao.SuperheroOrganizationsDao;
import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superhero;
import com.sg.superherosightings.model.SuperheroOrganizations;

import java.util.List;
import javax.inject.Inject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jswan
 */

public class SetupSuperherosUnitTests {

    SightingDao sightingDao;
    SuperheroOrganizationsDao superOrgsDao;
    OrganizationDao organizationDao;
    LocationDao locationDao;
    SuperheroDao superDao;



    @Inject
    public SetupSuperherosUnitTests() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");

        locationDao = ctx.getBean("locationDao", LocationDao.class);
        superDao = ctx.getBean("superheroDao", SuperheroDao.class);
        sightingDao = ctx.getBean("sightingDao", SightingDao.class);
        organizationDao = ctx.getBean("organizationDao", OrganizationDao.class);
        superOrgsDao = ctx.getBean("superOrgDao", SuperheroOrganizationsDao.class);
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting currentSighting : sightings) {
            sightingDao.deleteSighting(currentSighting.getSightId());
        }

        List<SuperheroOrganizations> superOrgList = superOrgsDao.getAllSuperheroOrganizations();
        for (SuperheroOrganizations currentSuperOrg : superOrgList) {
            superOrgsDao.deleteSuperheroOrganizations(currentSuperOrg);
        }

        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            organizationDao.deleteOrganization(currentOrganization.getOrganizationId());
        }

        List<Superhero> superheroList = superDao.getAllSuperheros();
        for (Superhero currentSuperhero : superheroList) {
            superDao.deleteSuperhero(currentSuperhero.getSuperheroId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location currentCurrent : locations) {
            locationDao.deleteLocation(currentCurrent.getLocationId());
        }

    }

    public Location createLocation1() {
        Location newLocal = new Location();
        newLocal.setLocationName("SouthWest General");
        newLocal.setLocationDescription("Hospital");
        newLocal.setAddress("18697 Bagley Rd");
        newLocal.setCity("Middleburg Heights");
        newLocal.setState("OH");
        newLocal.setZip("44130");
        newLocal.setLatitude(41.370249);
        newLocal.setLongitude(-81.832177);

        return newLocal;
    }

    public Location createLocation2() {
        Location newLocal = new Location();
        newLocal.setLocationName("16-bit");
        newLocal.setLocationDescription("Gamers Bar");
        newLocal.setAddress("15012 Detroit Ave");
        newLocal.setCity("Lakewood");
        newLocal.setState("OH");
        newLocal.setZip("44107");
        newLocal.setLatitude(41.485315);
        newLocal.setLongitude(-81.801334);

        return newLocal;
    }

    public Location createLocation3() {
        Location local3 = new Location();
        local3.setLocationName("BrookPark Urgent Care");
        local3.setLocationDescription("Urgent Care");
        local3.setAddress("15900 Snow Rd #200");
        local3.setCity("Brook Park");
        local3.setState("OH");
        local3.setZip("44142");
        local3.setLatitude(41.405279);
        local3.setLongitude(-81.809310);

        return local3;
    }

    public Superhero createSuperhero1() {
        Superhero super1 = new Superhero();
        super1.setSuperheroName("Chief Wiggum");
        super1.setSuperheroDescription("GoodGuy");
        super1.setSuperpower("People protector power");

        return super1;
    }

    public Superhero createSuperhero2() {
        Superhero super1 = new Superhero();
        super1.setSuperheroName("PacMan");
        super1.setSuperheroDescription("Yellow Classic");
        super1.setSuperpower("Eatter Of Purple Ghosts");

        return super1;
    }

    public Superhero createSuperhero3() {
        Superhero super1 = new Superhero();
        super1.setSuperheroName("Sphinx");
        super1.setSuperheroDescription("Mysterious");
        super1.setSuperpower("Terribly Mysterious");

        return super1;
    }

    public Organization createOrganization1() {
        //Location local1 = this.createLocation1();
        Location local1 = locationDao.getLocationByAddressZip(this.createLocation1());
        Organization organization = new Organization();
        organization.setOrganizationName("Avengers");
        organization.setOrganizationDescription("Wayne Manor");
        organization.setOrganizationContact("911");
        organization.setLocation(local1);

        return organization;
    }

    public Organization createOraganization2() {
        //Location local = this.createLocation3();
        Location local = locationDao.getLocationByAddressZip(this.createLocation3());
        Organization organization = new Organization();
        organization.setOrganizationName("Animaniacs");
        organization.setOrganizationDescription("cartooon crazies!");
        organization.setOrganizationContact("babs bunny");
        organization.setLocation(local);

        return organization;
    }

}
