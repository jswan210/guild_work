/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.mappers;

import com.sg.superherosightings.model.Superhero;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author jswan
 */
public class SuperheroMapper implements RowMapper<Superhero> {

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
