/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.mappers.OrganizationMapper;
import com.sg.superherosightings.mappers.SuperheroMapper;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhero;
import com.sg.superherosightings.model.SuperheroOrganizations;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jswan
 */
public class SuperheroOrganizationsDaoJdbcTemplateImpl implements SuperheroOrganizationsDao {

    private SuperheroMapper superheroMapper;
    private OrganizationMapper organizationMapper;
    private JdbcTemplate jdbcTemplate;
    private SuperheroDao superDao;
    private OrganizationDao orgDao;

    public SuperheroOrganizationsDaoJdbcTemplateImpl(SuperheroDao superDao, OrganizationDao orgDao) {
        this.superDao = superDao;
        this.orgDao = orgDao;
    }

    public void setSuperheroMapper(SuperheroMapper superheroMapper) {
        this.superheroMapper = superheroMapper;

    }

    public void setOrganizationMapper(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //prepared Statemetns
    static final String SQL_INSERT_SUPER_ORGANIZATION
            = " INSERT INTO super_organization_affiliation (super_id, organization_id)"
            + " values(?,?)";

    static final String SQL_DELETE_SUPER_ORGANIZATION
            = "delete from super_organization_affiliation"
            + " where super_id =? and organization_id =?";

    static final String SQL_SELECT_BY_SUPERHEROS_IN_ORG
            = "select soa.super_id, soa.organization_id "
            + " from super_organization_affiliation soa"
            + " join superheros s on  soa.super_id = s.super_id"
            + " join organizations o on soa.organization_id = o.organization_id"
            + " where soa.organization_id = ?";

    static final String SQL_SELECT_ORGANIZATIONS_SUPERHERO
            = "select soa.super_id, soa.organization_id"
            + " from super_organization_affiliation soa"
            + " join superheros s on  soa.super_id = s.super_id"
            + " where soa.super_id = ?";

    static final String SQL_GET_ALL_SUPER_ORGANIZATIONS
            = "select * from super_organization_affiliation";

    static final String SQL_SELECT_BY_HERO_ID_AND_ORG_ID
            = "select * from super_organization_affiliation"
            + " where super_id = ? and organization_id = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public SuperheroOrganizations addSuperheroOrganizations(SuperheroOrganizations superOrg) {
        jdbcTemplate.update(SQL_INSERT_SUPER_ORGANIZATION,
                superOrg.getSuperhero().getSuperheroId(),
                superOrg.getOrganization().getOrganizationId());

        return superOrg;
    }

    @Override
    public void deleteSuperheroOrganizations(SuperheroOrganizations superOrg) {
        jdbcTemplate.update(SQL_DELETE_SUPER_ORGANIZATION,
                superOrg.getSuperhero().getSuperheroId(),
                superOrg.getOrganization().getOrganizationId());
    }

//============ NOT SURE UPDATE WILL BE NEEDED....===============================
    //my take is for this one they are either attached or not...so any updates are just to delete...
    // @Override
    // public SuperheroOrganizations updateSuperheroOrganizations
    //(SuperheroOrganizations superheroOrganizations) {
    //     throw new UnsupportedOperationException("Not supported yet."); 
    //To change body of generated methods, choose Tools | Templates.
    // }    
//==============================================================================
    @Override
    public List<SuperheroOrganizations> getSuperheroOrganizationByOrganization(int organizationId) {
        List<SuperheroOrganizations> superOrgList = jdbcTemplate.query(SQL_SELECT_BY_SUPERHEROS_IN_ORG,
                new SuperheroOrganizationsMapper(), organizationId);
        for (SuperheroOrganizations currentSuperOrg : superOrgList) {
            this.getAllSuperheroAndOrgEntities(currentSuperOrg);
        }
        return superOrgList;
    }

    @Override
    public List<SuperheroOrganizations> getSuperheroOrganizationsBySuperhero(int superheroId) {
        List<SuperheroOrganizations> superOrgList = jdbcTemplate.query(SQL_SELECT_ORGANIZATIONS_SUPERHERO,
                new SuperheroOrganizationsMapper(),
                superheroId);
        for (SuperheroOrganizations currentSuperOrg : superOrgList) {
            this.getAllSuperheroAndOrgEntities(currentSuperOrg);
        }
        return superOrgList;
    }

    @Override
    public List<SuperheroOrganizations> getAllSuperheroOrganizations() {
        List<SuperheroOrganizations> superOrgList = jdbcTemplate.query(SQL_GET_ALL_SUPER_ORGANIZATIONS,
                new SuperheroOrganizationsMapper());

        for (SuperheroOrganizations currentSuperOrg : superOrgList) {
            this.getAllSuperheroAndOrgEntities(currentSuperOrg);
        }
        return superOrgList;
    }

    @Override
    public SuperheroOrganizations getSuperOrgBySuperIdAndOrgId(SuperheroOrganizations superOrg) {
        try {
            SuperheroOrganizations fillSuperOrg = jdbcTemplate.queryForObject(SQL_SELECT_BY_HERO_ID_AND_ORG_ID,
                    new SuperheroOrganizationsMapper(),
                    superOrg.getSuperhero().getSuperheroId(),
                    superOrg.getOrganization().getOrganizationId());
            return getAllSuperheroAndOrgEntities(fillSuperOrg);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    //Helper Methods
    //====================================================
    private SuperheroOrganizations getAllSuperheroAndOrgEntities(SuperheroOrganizations superOrg) {
        superOrg.setSuperhero(superDao.getSuperheroById(superOrg.getSuperhero().getSuperheroId()));
        superOrg.setOrganziation(orgDao.getOrganizationById(superOrg.getOrganization().getOrganizationId()));
        return superOrg;
    }

    private static final class SuperheroOrganizationsMapper implements RowMapper<SuperheroOrganizations> {

        @Override
        public SuperheroOrganizations mapRow(ResultSet rs, int i) throws SQLException {
            Superhero superhero = new Superhero();
            Organization org = new Organization();
            superhero.setSuperheroId(rs.getInt("super_id"));
            org.setOrganizationId(rs.getInt("organization_id"));
            SuperheroOrganizations superOrgs = new SuperheroOrganizations();
            superOrgs.setSuperhero(superhero);
            superOrgs.setOrganziation(org);

            return superOrgs;
        }
    }

}
