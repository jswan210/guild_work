/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.mappers.LocationMapper;
import com.sg.superherosightings.mappers.OrganizationMapper;
import com.sg.superherosightings.mappers.SuperheroMapper;
import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jswan
 */
public class OrganizationDaoJdbcTemplateImpl implements OrganizationDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Prepared Statements
    private static final String SQL_INSERT_ORGANIZATION
            = "insert ignore into Organizations (organization_name,"
            + " organization_description, organization_contact, location_id)"
            + "values(?,?,?,?)";

    private static final String SQL_DELETE_ORGANIZATION
            = "delete from Organizations where organization_id = ?";

    private static final String SQL_UPDATE_ORGANIZATION
            = "update Organizations set organization_name = ?, "
            + "organization_description = ?, organization_contact = ?, "
            + "location_id = ? "
            + "where organization_id = ?";

    private static final String SQL_SELECT_ORGANIZATION_BY_ID
            = "select * from organizations where organization_id = ?";

    private static final String SQL_SELECT_ALL_ORGANIZATIONS
            = "select * from organizations ";

    private static final String SQL_SELECT_LOCAL_BY_ORG_ID
            = "select loc.location_id, loc.location_name, loc.location_description, loc.address, "
            + "loc.city, loc.state, loc.zip, loc.latitude, loc.longitude"
            + " from locations loc join organizations org on "
            + "loc.location_id = org.location_id "
            + "where org.organization_id = ?";

    private static final String SQL_SELECT_ORG_BY_LOCATION_ID
            = "select * from organizations "
            + "where organizations.location_id = ?";

    private static final String SQL_SELECT_ORG_BY_NAME_AND_LOCATION
            = "select * from organizations "
            + "where organization_name = ? and location_id = ? ";

    private static final String SQL_SELECT_ORG_BY_NAME
            = "select * from organizations where organization_name = ?";

    private static final String SQL_SELECT_ALL_MEMBERS_OF_AN_ORGANIZATION
            = "select s.super_id, s.superhero_name, s.description, s.superpower "
            + "from superheros s "
            + "join super_organization_affiliation soa on s.super_id = soa.super_id "
            + "join organizations o on soa.organization_id = o.organization_id "
            + "where o.organization_id = ? ";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization addOrganization(Organization organization) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getOrganizationDescription(),
                organization.getOrganizationContact(),
                organization.getLocation().getLocationId());
        int organizationId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);
        organization.setOrganizationId(organizationId);
        return organization;
    }

    @Override
    public void deleteOrganization(int organizationId) {
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationId);
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getOrganizationDescription(),
                organization.getOrganizationContact(),
                organization.getLocation().getLocationId(),
                organization.getOrganizationId());
        return organization;
    }

    @Override
    public Organization getOrganizationById(int organizationId) {
        try {
            Organization org = jdbcTemplate
                    .queryForObject(SQL_SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(),
                            organizationId);
            Location loc = this.findLocationForOrganization(org);
            org.setLocation(loc);
            return org;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    public List<Organization> getAllOrganizations() {
        List<Organization> orgList = jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS,
                new OrganizationMapper());
        for (Organization currentOrganization : orgList) {
            currentOrganization.setMembers(jdbcTemplate.query(SQL_SELECT_ALL_MEMBERS_OF_AN_ORGANIZATION,
                    new SuperheroMapper(), currentOrganization.getOrganizationId()));
        }
        return associateLocationWithOrganization(orgList);
    }

    @Override
    public Organization getOrganizationByLocationId(int locationId) {

        try {
            Organization org = jdbcTemplate.queryForObject(SQL_SELECT_ORG_BY_LOCATION_ID,
                    new OrganizationMapper(), locationId);
            Location loc = this.findLocationForOrganization(org);
            org.setLocation(loc);
            return org;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Organization getOrganizationByNameAndLocationId(Organization organization) {
        try {
            Organization org = jdbcTemplate
                    .queryForObject(SQL_SELECT_ORG_BY_NAME_AND_LOCATION, new OrganizationMapper(),
                            organization.getOrganizationName(),
                            organization.getLocation().getLocationId());
            Location loc = this.findLocationForOrganization(org);
            org.setLocation(loc);
            return org;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Organization getOrganizationByName(Organization organization) {
        try {
            Organization org = jdbcTemplate.queryForObject(SQL_SELECT_ORG_BY_NAME,
                    new OrganizationMapper(), organization.getOrganizationName());
            org.setLocation(findLocationForOrganization(org));
            return org;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

//Helper Method
    private List<Organization> associateLocationWithOrganization(List<Organization> orgList) {
        for (Organization currentOrganization : orgList) {
            currentOrganization.setLocation(findLocationForOrganization(currentOrganization));
        }
        return orgList;
    }

    private Location findLocationForOrganization(Organization organization) {
        return jdbcTemplate.queryForObject(SQL_SELECT_LOCAL_BY_ORG_ID,
                new LocationMapper(),
                organization.getOrganizationId());
    }

}
