/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Organization;
import java.util.List;

/**
 *
 * @author jswan
 */
public interface OrganizationDao {

    public Organization addOrganization(Organization organization);

    public void deleteOrganization(int organizationId);

    public Organization updateOrganization(Organization organization);

    public Organization getOrganizationById(int organizationId);

    public List<Organization> getAllOrganizations();

    public Organization getOrganizationByLocationId(int locationId);

    public Organization getOrganizationByNameAndLocationId(Organization organziation);

    public Organization getOrganizationByName(Organization organization);
}
