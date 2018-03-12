/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

/**
 *
 * @author jswan
 */
public class GivenStateIsInvalidException extends Exception {

    public GivenStateIsInvalidException(String message) {
        super(message);
    }
    public GivenStateIsInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
