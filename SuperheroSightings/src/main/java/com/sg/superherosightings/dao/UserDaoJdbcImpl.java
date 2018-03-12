/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author jswan
 */
public class UserDaoJdbcImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //PREPARED STATEMENTS
    private static final String SQL_INSERT_USER
            = "insert into users (username, password, enabled) values (?, ?, 1)";

    private static final String SQL_SELECT_USER_BY_ID
            = " select * from users where user_id = ? ";

    private static final String SQL_DELETE_USER
            = "delete from users where user_id = ?";

    private static final String SQL_UPDATE_USER
            = "update users set username= ?, password = ?"
            + " where user_id = ?";

    private static final String SQL_UPDATE_USERNAME_ONLY
            = "update users set username= ? where user_id = ?";

    private static final String SQL_INSERT_AUTHORITY
            = "insert ignore into authorities (user_id, authority) values (?, ?)";

    private static final String SQL_DELETE_AUTHORITIES
            = "delete from authorities where user_id = ?";

    private static final String SQL_GET_ALL_USERS
            = "select * from users";

    private static final String SQL_GET_USERS_AUTHORITIES
            = " select authority from authorities where user_id = ? ";

    @Override
    public User addUser(User newUser) throws DuplicateUserNameException {
        boolean checkName = checkUserForDuplicateNameForAddNew(newUser);
        if (checkName != true) {
            throw new DuplicateUserNameException("Name already Exists: Cannot Duplicate");
        }
        jdbcTemplate.update(SQL_INSERT_USER,
                newUser.getUsername(),
                newUser.getPassword());
        int userId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        newUser.setId(userId);

// now insert user's roles
        ArrayList<String> authorities = newUser.getAuthorities();
        Set<String> setAuthorities = authorities.stream().collect(Collectors.toSet());

        for (String authority : setAuthorities) {
            jdbcTemplate.update(SQL_INSERT_AUTHORITY,
                    newUser.getId(),
                    authority);
        }

        return newUser;

    }

    @Override
    public User getUserById(int userId) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_ID,
                    new UserMapper(),
                    userId);
            List<String> authorityList = jdbcTemplate.query(SQL_GET_USERS_AUTHORITIES,
                    new AuthorityMapper(), user.getId());
            for (String currentAuthority : authorityList) {
                user.addAuthority(currentAuthority);
            }
            return user;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void deleteUser(int userId) {
        // first delete all authorities for this user
        jdbcTemplate.update(SQL_DELETE_AUTHORITIES, userId);
        // secondjdbcTemplate.update(SQL_DELETE_AUTHORITIES, username); delete the user
        jdbcTemplate.update(SQL_DELETE_USER, userId);
    }

    @Override
    public User updateUser(User updateUser) throws DuplicateUserNameException {
        User ourUser = updateUser;
        boolean checkName = checkUsersForDuplicateNameForUpdate(updateUser);
        if (checkName != true) {
            throw new DuplicateUserNameException("Name already Exists: Cannot Duplicate");
        }

        jdbcTemplate.update(SQL_DELETE_AUTHORITIES, updateUser.getId());

        jdbcTemplate.update(SQL_UPDATE_USER, ourUser.getUsername(),
                ourUser.getPassword(), ourUser.getId());

        ArrayList<String> authorities = ourUser.getAuthorities();
        for (String authority : authorities) {
            jdbcTemplate.update(SQL_INSERT_AUTHORITY,
                    ourUser.getId(),
                    authority);
        }

        return ourUser;
    }

    @Override
    public User deleteAuthorityList(User user) {
        int numberOfAuthorities = user.getAuthorities().size();
        for (int x = 0; x < numberOfAuthorities; x++) {
            user.getAuthorities().remove(0);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = jdbcTemplate.query(SQL_GET_ALL_USERS, new UserMapper());

        for (User currentUser : userList) {
            List<String> authorityList = jdbcTemplate.query(SQL_GET_USERS_AUTHORITIES,
                    new AuthorityMapper(), currentUser.getId());
            for (String currentAuthority : authorityList) {
                currentUser.addAuthority(currentAuthority);
            }

        }

        return userList;
    }

    private boolean checkUserForDuplicateNameForAddNew(User user) throws DuplicateUserNameException {
        List<User> userList = this.getAllUsers();
        for (User currentUser : userList) {
            if (currentUser.getUsername().equalsIgnoreCase(user.getUsername())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkUsersForDuplicateNameForUpdate(User user) throws DuplicateUserNameException {
        List<User> userList = this.getAllUsers();
        for (User currentUser : userList) {
            if (currentUser.getId() != user.getId()) {
                if (currentUser.getUsername().equalsIgnoreCase(user.getUsername())) {
                    return false;
                }
            }

        }
        return true;
    }

    private static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));

            return user;
        }
    }

    private static final class AuthorityMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("authority");

        }
    }

}
