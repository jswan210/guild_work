/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.LocationDao;
import com.sg.superherosightings.dao.OrganizationDao;
import com.sg.superherosightings.dao.SuperheroOrganizationsDao;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.SuperheroOrganizations;
import java.util.List;
import javax.inject.Inject;


/**
 *
 * @author jswan
 */
public class OrganizationServiceImpl implements OrganizationService {

    private LocationDao locDao;
    private OrganizationDao orgDao;
    private SuperheroOrganizationsDao superOrgDao;

    @Inject
    public OrganizationServiceImpl(LocationDao locDao,
            OrganizationDao orgDao,
            SuperheroOrganizationsDao superOrgDao) {
        this.locDao = locDao;
        this.orgDao = orgDao;
        this.superOrgDao = superOrgDao;
    }

    @Override
    public Organization addOrganization(Organization organization) throws
            OrganizationNameAlreadyExistsException,
            LocationExistsInOrganizationException {

        boolean checkOrg = checkIfOrgNameAlreadyExists(organization);
        if (checkOrg != true) {
            throw new OrganizationNameAlreadyExistsException("Organization Already exists!!");
        }

        boolean checkLocsInOrg = checkIfLocationAlreadyExsitsInOrganization(organization);
        if (checkLocsInOrg != true) {
            throw new LocationExistsInOrganizationException("Location AlreadyExists In An Organization");
        }
        return orgDao.addOrganization(organization);
    }

    @Override
    public void deleteOrganization(Organization organization) throws
            OrganizationLinksExistsInSuperOrgException {
        // check if any superheros are affiliated with an organization:
        List<SuperheroOrganizations> superOrgList
                = superOrgDao.getSuperheroOrganizationByOrganization(organization.getOrganizationId());
        if (superOrgList.size() > 0) {
            throw new OrganizationLinksExistsInSuperOrgException("Organization is linked "
                    + "to various Superheros...need to take care of that first...");
        }
        orgDao.deleteOrganization(organization.getOrganizationId());

    }

    @Override
    public void updateOrganization(Organization organization) throws
            OrganizationNameAlreadyExistsException, LocationExistsInOrganizationException {
        boolean checkOrg = checkOrgNameForUpdate(organization);
        if (checkOrg != true) {
            throw new OrganizationNameAlreadyExistsException("Organization Already exists!!");
        }

        boolean checkLocsInOrg = checkIfLocExsitsInOrganizationForUpdate(organization);
        if (checkLocsInOrg != true) {
            throw new LocationExistsInOrganizationException("Location AlreadyExists In An Organization");
        }
        orgDao.updateOrganization(organization);
    }

    @Override
    public Organization getOrganizationById(int organizationId) {
        return orgDao.getOrganizationById(organizationId);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return orgDao.getAllOrganizations();
    }

    @Override
    public Organization getOrganizationByLocationId(int locationId) {
        return orgDao.getOrganizationByLocationId(locationId);
    }

    // ---- validation methods ----
    private boolean checkIfOrgNameAlreadyExists(Organization organization) {
        Organization checkOrg = orgDao.getOrganizationByName(organization);
        return checkOrg == null;
    }

    private boolean checkIfLocationAlreadyExsitsInOrganization(Organization organization) {
        Organization orgCheck = orgDao.getOrganizationByLocationId(organization.getLocation().getLocationId());
        return orgCheck == null;
    }

    //Checks if Organization Name already Exists, specified for Update!
    private boolean checkOrgNameForUpdate(Organization organization) {
        Organization checkOrg = orgDao.getOrganizationByName(organization);
        return checkOrg == null || checkOrg.getOrganizationId() == organization.getOrganizationId();

    }

    //Checks if Location exists in an Organization...specified for Update!
    private boolean checkIfLocExsitsInOrganizationForUpdate(Organization organization) {
        Organization orgCheck = orgDao.getOrganizationByLocationId(organization.getLocation().getLocationId());
        return orgCheck == null || orgCheck.getOrganizationId() == organization.getOrganizationId();
    }

}
