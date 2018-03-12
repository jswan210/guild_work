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
public class SightingAlreadyExistsException extends Exception {

    /**
     * Constructs an instance of <code>SightingAlreadyExistsException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public SightingAlreadyExistsException(String msg) {
        super(msg);
    }

    public SightingAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
