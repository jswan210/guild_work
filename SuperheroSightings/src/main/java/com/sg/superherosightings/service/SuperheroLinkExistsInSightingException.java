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
public class SuperheroLinkExistsInSightingException extends Exception {

    /**
     * Constructs an instance of
     * <code>SuperheroLinkExistsInSightingException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public SuperheroLinkExistsInSightingException(String msg) {
        super(msg);
    }

    public SuperheroLinkExistsInSightingException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
