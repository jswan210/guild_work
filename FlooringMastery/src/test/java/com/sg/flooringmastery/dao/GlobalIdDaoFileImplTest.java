/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jswan
 */
public class GlobalIdDaoFileImplTest {

    private GlobalIdDao globalIdDao;

    public GlobalIdDaoFileImplTest() throws GlobalIdInfoPersistenceException {
        this.globalIdDao = new GlobalIdDaoStubImpl();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getNewID() throws Exception {
        int ourNumber = globalIdDao.getNewID();

        assertEquals(2, ourNumber);

        int ourNumber2 = globalIdDao.getNewID();

        assertEquals(3, ourNumber2);

    }

}
