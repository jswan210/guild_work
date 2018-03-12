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
public class LocationExistsInOrganizationException extends Exception {

    /**
     * Creates a new instance of
     * <code>LocationExitsInOrganizationException</code> without detail message.
     */
    public LocationExistsInOrganizationException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of
     * <code>LocationExitsInOrganizationException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public LocationExistsInOrganizationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return "LocationExitsInOrganizationException{" + "Update or Delete Organization "
                + "before continuing}";
    }

}
