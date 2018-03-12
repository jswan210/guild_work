/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.mappers.OrganizationMapper;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhero;
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
public class SuperheroDaoJdbcTemplateImpl implements SuperheroDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Prepared Statements
    private static final String SQL_INSERT_SUPERHERO
            = "insert ignore into superheros (superhero_name,"
            + "description, superpower)"
            + " values( ?, ?, ?)";
            
    private static final String SQL_DELETE_SUPERHERO
            = "delete from superheros where super_id = ?";
            
    private static final String SQL_UPDATE_SUPERHERO
            = "update superheros set superhero_name = ?,"
            + " description = ?, superpower = ?"
            + " where super_id = ?";
            
    private static final String SQL_SELECT_SUPERHERO_BY_ID
            = "select * from superheros where super_id = ?";
            
    private static final String SQL_SELECT_ALL_SUPERHEROS
            = "select * from superheros";

    private static final String SQL_SELECT_SUPERHERO_BY_NAME
            = "select * from superheros where superhero_name =? ";

    private static final String SQL_SELECT_ALL_ORGS_BY_SUPER_ID
            = "select o.organization_id, o.organization_name, o.organization_description, "
            + "o.organization_contact, o.location_id from organziation o "
            + "join super_organization_affiliation soa on o.organization_id = soa.organization_id "
            + "where superhero_id = ?";

    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Superhero addSuperhero(Superhero superhero) {
        jdbcTemplate.update(SQL_INSERT_SUPERHERO,
                superhero.getSuperheroName(),
                superhero.getSuperheroDescription(),
                superhero.getSuperpower());
        
        int superheroId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);
        superhero.setSuperheroId(superheroId);

        return superhero;
    }

    @Override
    public void deleteSuperhero(int superheroId) {
        jdbcTemplate.update(SQL_DELETE_SUPERHERO, superheroId);

    }

    @Override
    public Superhero updateSuperhero(Superhero superhero) {
        jdbcTemplate.update(SQL_UPDATE_SUPERHERO,
                superhero.getSuperheroName(),
                superhero.getSuperheroDescription(),
                superhero.getSuperpower(),
                superhero.getSuperheroId());

        return superhero;
    }

    @Override
    public Superhero getSuperheroById(int superheroId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_SUPERHERO_BY_ID,
                    new SuperheroMapper(),
                    superheroId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superhero> getAllSuperheros() {
        return jdbcTemplate.query(SQL_SELECT_ALL_SUPERHEROS,
                new SuperheroMapper());
    }

    @Override
    public Superhero getSuperheroByName(Superhero superhero) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_SUPERHERO_BY_NAME,
                    new SuperheroMapper(),
                    superhero.getSuperheroName());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrgsAssociatedWithSuperhero(int superheroId) {
        return jdbcTemplate.query(SQL_SELECT_ALL_ORGS_BY_SUPER_ID, new OrganizationMapper(),
                superheroId);
    }


    //Mapper
    private static final class SuperheroMapper implements RowMapper<Superhero> {

        @Override
        public Superhero mapRow(ResultSet rs, int i) throws SQLException {
            Superhero s = new Superhero();
            s.setSuperheroId(rs.getInt("super_id"));
            s.setSuperheroName(rs.getString("superhero_name"));
            s.setSuperheroDescription(rs.getString("description"));
            s.setSuperpower(rs.getString("superpower"));
            return s;
        }
    }

}
