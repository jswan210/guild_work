/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.SuperheroOrganizationsDao;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhero;
import com.sg.superherosightings.model.SuperheroOrganizations;
import java.util.List;
import javax.inject.Inject;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author jswan
 */
public class SuperheroOrganizationServiceImpl implements SuperheroOrganizationService {

    private SuperheroOrganizationsDao superOrgDao;

    //@Inject
    public SuperheroOrganizationServiceImpl(
            SuperheroOrganizationsDao superOrgDao) {
        this.superOrgDao = superOrgDao;
    }

    @Override
    public SuperheroOrganizations addSuperheroOrganizations(Superhero superhero,
            Organization organization) throws SuperheroLinkExistsInSuperOrganizationException {
        SuperheroOrganizations superOrg = new SuperheroOrganizations();
        superOrg.setSuperhero(superhero);
        superOrg.setOrganziation(organization);

        boolean checkSuperOrgDuplicate = checkDuplicateSuperOrgAffiliation(superOrg);

        if (checkSuperOrgDuplicate != true) {
            throw new SuperheroLinkExistsInSuperOrganizationException("Link exists between Superhero and "
                    + "Organization already...no need to dulicate");
        }

        return superOrgDao.addSuperheroOrganizations(superOrg);
    }

    private boolean checkDuplicateSuperOrgAffiliation(SuperheroOrganizations superOrg) {
        SuperheroOrganizations checkSuperOrgDuplicate = superOrgDao.getSuperOrgBySuperIdAndOrgId(superOrg);
        return checkSuperOrgDuplicate == null;
    }

    @Override
    public void deleteSuperheroOrganizations(SuperheroOrganizations superheroOrganizations) {
        superOrgDao.deleteSuperheroOrganizations(superheroOrganizations);
    }

    @Override
    public List<SuperheroOrganizations> getSuperheroOrganizationByOrganization(int organizationId) {
        return superOrgDao.getSuperheroOrganizationByOrganization(organizationId);

    }

    @Override
    public List<SuperheroOrganizations> getSuperheroOrganizationsBySuperhero(int superheroId) {
        return superOrgDao.getSuperheroOrganizationsBySuperhero(superheroId);
    }

    @Override
    public List<SuperheroOrganizations> getAllSuperheroOrganizations() {
        return superOrgDao.getAllSuperheroOrganizations();
    }

    @Override
    public SuperheroOrganizations getSuperOrgBySuperIdAndOrgId(SuperheroOrganizations superOrg) {
        return superOrgDao.getSuperOrgBySuperIdAndOrgId(superOrg);
    }

}
