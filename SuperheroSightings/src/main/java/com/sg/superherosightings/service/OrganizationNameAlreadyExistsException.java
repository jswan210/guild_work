/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

/**
 *
 * @author jswan
 */
public class OrganizationNameAlreadyExistsException extends Exception {

    public OrganizationNameAlreadyExistsException(String msg) {
        super(msg);
    }

    public OrganizationNameAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
