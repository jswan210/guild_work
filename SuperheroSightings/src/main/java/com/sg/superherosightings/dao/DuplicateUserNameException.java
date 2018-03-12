/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

/**
 *
 * @author jswan
 */
public class DuplicateUserNameException extends Exception {

    /**
     * Creates a new instance of <code>DuplicateUserNameException</code> without
     * detail message.
     */
    public DuplicateUserNameException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of <code>DuplicateUserNameException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DuplicateUserNameException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return "DuplicateUserNameException: Cannot Duplicate User Name";
    }
}
