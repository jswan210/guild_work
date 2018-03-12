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
public class LocationExistsInSightingException extends Exception {

    /**
     * Creates a new instance of <code>LocationExistsInSightingException</code>
     * without detail message.
     */
    public LocationExistsInSightingException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of <code>LocationExistsInSightingException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public LocationExistsInSightingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public String toString() {
        return "LocationExitsInSightingException{" + "Update or Delete Sightings "
                + "before continuing}";
    }
}
