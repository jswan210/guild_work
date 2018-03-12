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
public class SuperheroLinkExistsInSuperOrganizationException extends Exception {

    /**
     * Creates a new instance of
     * <code>SuperheroExistsInOrganizationException</code> without detail
     * message.
     */
    public SuperheroLinkExistsInSuperOrganizationException(String msg) {
        super(msg);
    }

    public SuperheroLinkExistsInSuperOrganizationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
