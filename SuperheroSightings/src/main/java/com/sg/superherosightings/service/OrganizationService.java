/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.model.Organization;
import java.util.List;

/**
 *
 * @author jswan
 */
public interface OrganizationService {

    public Organization addOrganization(Organization organization)
            throws OrganizationNameAlreadyExistsException,
            LocationExistsInOrganizationException;

    public void deleteOrganization(Organization organization) throws
            OrganizationLinksExistsInSuperOrgException;

    public void updateOrganization(Organization organization) throws
            OrganizationNameAlreadyExistsException,
            LocationExistsInOrganizationException;

    public Organization getOrganizationById(int organizationId);

    public List<Organization> getAllOrganizations();

    public Organization getOrganizationByLocationId(int locationId);
}
