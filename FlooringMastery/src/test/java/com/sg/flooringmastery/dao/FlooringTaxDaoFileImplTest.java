/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Tax;
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
public class FlooringTaxDaoFileImplTest {

    private FlooringTaxDao taxDao = new FlooringTaxDaoFileImpl();

    public FlooringTaxDaoFileImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of obtainTaxRateByState method, of class FlooringTaxDaoFileImpl.
     */
    @Test
    public void testObtainTaxRateByState1() throws Exception {
        
        String state = "OH";
        Tax tax = taxDao.obtainTaxRateByState(state);
       
        double desiredOutcome = 6.25;
        assertTrue(tax.getTaxRate() == desiredOutcome);//,taxDao.obtainTaxRateByState(tax.getState()) );

    }
     /**
     * Test of obtainTaxRateByState method, of class FlooringTaxDaoFileImpl.
     * makes sure that the Tax object can be obtained and information gathered by 
     * the Order Class...
     */
        @Test
    public void testObtainTaxRateByState2() throws Exception {

        Order order = new Order();
        String state = "MI";
        Tax tax = taxDao.obtainTaxRateByState(state);
        order.setTax(tax);

        double desiredOutcome = 5.75;
        assertTrue(order.getTax().getTaxRate() == desiredOutcome);
    }

}
