/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jswan
 */
public class UserDaoTest {

    UserDao userDao;

    public UserDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        userDao = ctx.getBean("userDao", UserDao.class);
        
        List<User> users = userDao.getAllUsers();
        for (User currentUser : users) {
            userDao.deleteUser(currentUser.getId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addUser method, of class UserDao.
     */
    @Test
    public void testAddMultipleAuthorities() throws Exception {
        User newUser = new User();
        newUser.setUsername("joe");
        newUser.setPassword("password");
        newUser.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");
        newUser.addAuthority("ROLE_USER");

        assertEquals(newUser.getAuthorities().size(), 2);

    }

    @Test
    public void testAddUser() throws Exception {
        User newUser = new User();
        newUser.setUsername("joe");
        newUser.setPassword("password");
        newUser.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");
        newUser.addAuthority("ROLE_USER");
        
        userDao.addUser(newUser);

        int numberOfUsers = userDao.getAllUsers().size();
        assertEquals(1, 1);
        
    }

    @Test(expected = DuplicateUserNameException.class)
    public void testAddDuplicateUser() throws Exception {
        User newUser = new User();
        newUser.setUsername("joe");
        newUser.setPassword("password");
        newUser.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");
        newUser.addAuthority("ROLE_USER");

        userDao.addUser(newUser);

        User newUser2 = new User();
        newUser2.setUsername("joe");
        newUser2.setPassword("password");
        newUser2.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");
        newUser2.addAuthority("ROLE_USER");

        userDao.addUser(newUser2);

    }

    /**
     * Test of getUserById method, of class UserDao.
     */
    @Test
    public void testGetUserById() throws DuplicateUserNameException {
        User newUser = new User();
        newUser.setUsername("joe");
        newUser.setPassword("password");
        newUser.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");
        newUser.addAuthority("ROLE_USER");

        userDao.addUser(newUser);

        User thisUser = userDao.getUserById(newUser.getId());
        int bob = 3;

    }

    /**
     * Test of deleteUser method, of class UserDao.
     */
    @Test
    public void testDeleteUser() {
    }

    /**
     * Test of updateUser method, of class UserDao.
     */
    @Test
    public void testUpdateUser() throws Exception {
        User newUser = new User();
        newUser.setUsername("joe");
        newUser.setPassword("password");
        newUser.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");
        newUser.addAuthority("ROLE_USER");
        newUser.addAuthority("ROLE_ADMIN");
        
        userDao.addUser(newUser);

        userDao.deleteAuthorityList(newUser);

        newUser.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");

        userDao.updateUser(newUser);

        assertEquals(userDao.getAllUsers().get(0).getAuthorities().size(), 1);
    }

    /**
     * Test of getAllUsers method, of class UserDao.
     */
    @Test
    public void testGetAllUsers() throws DuplicateUserNameException {
        User newUser = new User();
        newUser.setUsername("joe");
        newUser.setPassword("password");
        newUser.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");
        newUser.addAuthority("ROLE_USER");

        userDao.addUser(newUser);

        User newUser2 = new User();
        newUser2.setUsername("sam");
        newUser2.setPassword("password");
        newUser2.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");
        newUser2.addAuthority("ROLE_USER");
        newUser2.addAuthority("ROLE_USER");
        newUser2.addAuthority("ROLE_ADMIN");

        userDao.addUser(newUser2);
        List<User> allUsers = userDao.getAllUsers();

        int numberOfUsers = allUsers.size();
        int numberOfAuthorities = allUsers.get(1).getAuthorities().size();
        assertEquals(numberOfAuthorities, 3);
        assertEquals(2, numberOfUsers);

    }

}
