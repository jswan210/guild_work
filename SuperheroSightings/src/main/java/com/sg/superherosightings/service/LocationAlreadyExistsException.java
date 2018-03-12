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
public class LocationAlreadyExistsException extends Exception {

    public LocationAlreadyExistsException(String msg) {
        super(msg);
    }

    public LocationAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
