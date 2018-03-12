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
public class FlooringOrderNumberDoesNotExistException extends Exception {

    public FlooringOrderNumberDoesNotExistException(String message) {
        super(message);
    }
    public FlooringOrderNumberDoesNotExistException( String message, Throwable cause){
        super (message, cause);
    }
    
}
