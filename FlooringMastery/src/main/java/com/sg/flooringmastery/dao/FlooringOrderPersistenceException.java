/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

/**
 *
 * @author jswan
 */
public class FlooringOrderPersistenceException extends Exception {

    public FlooringOrderPersistenceException(String message) {
        super(message);
    }

    public FlooringOrderPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
