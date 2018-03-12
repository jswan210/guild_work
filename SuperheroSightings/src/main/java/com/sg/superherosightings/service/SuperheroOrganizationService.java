/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhero;
import com.sg.superherosightings.model.SuperheroOrganizations;
import java.util.List;

/**
 *
 * @author jswan
 */
public interface SuperheroOrganizationService {

    public SuperheroOrganizations addSuperheroOrganizations(Superhero superhero,
            Organization organization) throws SuperheroLinkExistsInSuperOrganizationException;

    public void deleteSuperheroOrganizations(SuperheroOrganizations superheroOrganizations);

    //public SuperheroOrganizations updateSuperheroOrganizations(SuperheroOrganizations superheroOrganizations);
    public List<SuperheroOrganizations> getSuperheroOrganizationByOrganization(int organizationId);

    public List<SuperheroOrganizations> getSuperheroOrganizationsBySuperhero(int superheroId);

    public List<SuperheroOrganizations> getAllSuperheroOrganizations();

    public SuperheroOrganizations getSuperOrgBySuperIdAndOrgId(SuperheroOrganizations superOrg);

}
