/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
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
public class FlooringProductDaoTest {

    FlooringProductDao productDao = new FlooringProductDaoFileImpl();

    public FlooringProductDaoTest() {
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

    /**
     * Test of obtainProductInfoByMaterial method, of class FlooringProductDao.
     */
    @Test
    public void testObtainProductInfoByMaterial() throws Exception {
     String material = "Wood";
        Product product = productDao.obtainProductInfoByMaterial(material);

        BigDecimal desiredMaterialPriceOutcome = new BigDecimal("5.15");
        BigDecimal desiredLaborPriceOutcome = new BigDecimal("4.75");

        assertEquals(product.getMaterialCostPerSqFt(),
                 desiredMaterialPriceOutcome);

        assertEquals(product.getLaborCostPerSqFt(),
                 desiredLaborPriceOutcome);
    }


}


