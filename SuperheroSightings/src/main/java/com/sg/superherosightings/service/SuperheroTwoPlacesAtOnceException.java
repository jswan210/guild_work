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
public class SuperheroTwoPlacesAtOnceException extends Exception {

    public SuperheroTwoPlacesAtOnceException(String msg) {
        super(msg);
    }

    public SuperheroTwoPlacesAtOnceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
