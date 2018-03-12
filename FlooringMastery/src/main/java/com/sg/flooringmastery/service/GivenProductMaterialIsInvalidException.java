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
public class GivenProductMaterialIsInvalidException extends Exception {

    public GivenProductMaterialIsInvalidException(String message) {
        super(message);
    }

    public GivenProductMaterialIsInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
