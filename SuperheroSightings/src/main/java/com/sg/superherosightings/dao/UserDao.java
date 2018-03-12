/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.User;
import java.util.List;

/**
 *
 * @author jswan
 */
public interface UserDao {

    public User addUser(User newUser) throws DuplicateUserNameException;

    public User getUserById(int userId);

    public void deleteUser(int userId);

    public User updateUser(User updateUser) throws DuplicateUserNameException;

    public List<User> getAllUsers();

    public User deleteAuthorityList(User user);
}
