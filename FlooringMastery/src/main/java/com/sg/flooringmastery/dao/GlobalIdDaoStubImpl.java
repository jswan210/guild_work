/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import java.util.HashSet;

import java.util.Set;
/**
 *
 * @author jswan
 */
public class GlobalIdDaoStubImpl implements GlobalIdDao {

    private int nextNumber;
    Set<Integer> globalIDs = new HashSet<>();


    public GlobalIdDaoStubImpl() {

    }

    @Override
    public int getNewID() throws GlobalIdInfoPersistenceException {
        int number = maxId();
        nextNumber = ++number;
        globalIDs.add(nextNumber);


        return nextNumber;
    }

    private int maxId() {
        int number = 1;
        Set<Integer> entity = globalIDs;
        for (Integer k : entity) {
            if (k > number) {
                number = k;
            }
        }

        return number;
    }

}
