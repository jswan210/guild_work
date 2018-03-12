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
public class FlooringOrderIdValidationException extends Exception {
    public FlooringOrderIdValidationException(String message) {
        super(message);
    }

    public FlooringOrderIdValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
