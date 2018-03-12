/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.mappers.LocationMapper;
import com.sg.superherosightings.mappers.SuperheroMapper;
import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superhero;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
public class SightingDaoJdbcTemplateImpl implements SightingDao {

    private JdbcTemplate jdbcTemplate;
    private SuperheroDao superDao;
    private LocationDao locDao;

    public SightingDaoJdbcTemplateImpl(SuperheroDao superDao, LocationDao locDao) {
        this.superDao = superDao;
        this.locDao = locDao;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Prepared Statements
    static final String SQL_INSERT_SIGHTING
            = "insert into super_sightings (super_id, location_id, sighting_date)"
            + " values (?,?,?)";

    static final String SQL_DELETE_SIGHTING
            = " delete from super_sightings where sight_id= ?";

    static final String SQL_UPDATE_SIGHTING
            = "update super_sightings set super_id = ?, location_id = ?, "
            + "sighting_date = ? "
            + "where sight_id = ?";

    static final String SQL_SELECT_ALL_SIGHTINGS
            = " select * from super_sightings";

    static final String SQL_SELECT_SIGHTING_BY_SUPERHERO
            = "select * from super_sightings where super_id = ?";

    static final String SQL_SELECT_SIGHTING_BY_DATE
            = "select * from super_sightings where sighting_date = ?";

    static final String SQL_SELECT_SIGHTING_BY_LOCATION
            = "select * from super_sightings where location_id = ?";

    static final String SQL_SELECT_SIGHT_BY_SUPER_LOC_DATE
            = "select * from super_sightings where super_id = ? and location_id = ?"
            + " and sighting_date = ?";

    static final String SQL_SELECT_RECENT_TEN_SIGHTINGS
            = "select ss.sight_id, s.super_id, s.superhero_name, l.location_id, l.location_name, l.address, l.city, l.state, ss.Sighting_date "
            + "from super_sightings ss "
            + "join superheros s on ss.super_id = s.super_id "
            + "join locations l on ss.location_id = l.location_id "
            + "Order By ss.Sighting_date DESC "
            + "limit 10";

    static final String SQL_SELECT_SIGHTING_BY_ID
            = "select * from super_sightings where sight_id = ?";

    static final String SQL_LOCATION_BY_SIGHT_ID
            = "select loc.location_id, loc.location_name, loc.location_description, "
            + "loc.address, loc.city, loc.state, loc.zip, loc.latitude, loc.longitude "
            + "from locations loc "
            + "join super_sightings ss on loc.location_id= ss.location_id "
            + "where ss.sight_id= ? ";

    static final String SQL_SUPERHERO_BY_SIGHT_ID
            = "select s.superhero_id, s.superhero_name, s.description, s.superpower"
            + "from superheros s"
            + "join super_sightings ss on s.super_id= ss.super_id "
            + "where ss.sight_id= ? ";

    static final String SQL_SELECT_SIGHT_BY_HERO_DATE
            = "select * from super_sightings"
            + " where super_id = ? and sighting_date = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)

    public Sighting addSighting(Sighting sighting) {
        jdbcTemplate.update(SQL_INSERT_SIGHTING,
                sighting.getSuperhero().getSuperheroId(),
                sighting.getLocation().getLocationId(),
                sighting.getDate());
        int sightId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        sighting.setSightId(sightId);

        return sighting;
    }

    @Override
    public void deleteSighting(int sightId) {
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightId);
    }

    @Override
    public Sighting updateSighting(Sighting sighting) {
        jdbcTemplate.update(SQL_UPDATE_SIGHTING, sighting.getSuperhero().getSuperheroId(),
                sighting.getLocation().getLocationId(),
                sighting.getDate(),
                sighting.getSightId());
        return sighting;
    }

    @Override
    public Sighting getSightingById(int sightId) {
        try {
            Sighting sighting = jdbcTemplate.queryForObject(SQL_SELECT_SIGHTING_BY_ID,
                    new SightingMapper(),
                    sightId);
            sighting = getAllSuperheroAndLocEntities(sighting);

            return sighting;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Sighting getSightingBySuperLocDate(Sighting sighting) {
        try {
            Sighting newSighting = jdbcTemplate.queryForObject(SQL_SELECT_SIGHT_BY_SUPER_LOC_DATE,
                    new SightingMapper(), sighting.getSuperhero().getSuperheroId(),
                sighting.getLocation().getLocationId(), sighting.getDate());
            return getAllSuperheroAndLocEntities(newSighting);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Sighting getSightingBySuperDate(Sighting sighting) {
        try {
            Sighting newSighting = jdbcTemplate.queryForObject(SQL_SELECT_SIGHT_BY_HERO_DATE,
                    new SightingMapper(), sighting.getSuperhero().getSuperheroId(), sighting.getDate());
            return getAllSuperheroAndLocEntities(newSighting);

        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getSightingsByLocation(int locationId) {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_SIGHTING_BY_LOCATION, new SightingMapper(), locationId);
        for (Sighting currentSighting : sightingList) {
            this.getAllSuperheroAndLocEntities(currentSighting);
        }
        return sightingList;
    }

    @Override
    public List<Sighting> getSightingsByDate(Timestamp date) {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_SIGHTING_BY_DATE, new SightingMapper(), date);
        for (Sighting currentSighting : sightingList) {
            this.getAllSuperheroAndLocEntities(currentSighting);
        }
        return sightingList;
    }

    @Override
    public List<Sighting> getSightingsBySuperhero(int superheroId) {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_SIGHTING_BY_SUPERHERO, new SightingMapper(), superheroId);
        for (Sighting currentSighting : sightingList) {
            this.getAllSuperheroAndLocEntities(currentSighting);
        }
        return sightingList;
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS, new SightingMapper());
        for (Sighting currentSighting : sightingList) {
            this.getAllSuperheroAndLocEntities(currentSighting);
        }
        return sightingList;
    }

    @Override
    public List<Sighting> getRecentTenSightings() {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_RECENT_TEN_SIGHTINGS, new SightingMapper());
        for (Sighting currentSighting : sightingList) {
            this.getAllSuperheroAndLocEntities(currentSighting);
        }
        return sightingList;
    }

    //helpler..
    private Sighting getAllSuperheroAndLocEntities(Sighting sighting) {

        sighting.setSuperhero(superDao.getSuperheroById(sighting.getSuperhero().getSuperheroId()));
        sighting.setLocation(locDao.getLocationById(sighting.getLocation().getLocationId()));
        return sighting;
    }

    //mapper
    private static class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            Superhero superhero = new Superhero();
            Location location = new Location();
            Sighting s = new Sighting();
            s.setSightId(rs.getInt("sight_id"));
            s.setSuperhero(superhero);
            s.setLocation(location);
            s.getSuperhero().setSuperheroId(rs.getInt("super_id"));
            s.getLocation().setLocationId(rs.getInt("location_id"));
            try {
                s.setDate(Timestamp.valueOf(LocalDateTime.parse(rs.getTimestamp("sighting_date").toString(), formatter)));
            } catch (DateTimeParseException ex) {
                String stringDate = rs.getString("sighting_date");
                String newString = stringDate.substring(0, 10) + "T" + stringDate.substring(11, 19);

                s.setDate(Timestamp.valueOf(LocalDateTime.parse(newString)));
            }
            return s;
        }

    }

}
