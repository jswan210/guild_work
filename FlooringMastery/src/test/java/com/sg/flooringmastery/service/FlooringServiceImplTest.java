/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringOrderPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jswan
 */

public class FlooringServiceImplTest {

    private FlooringServiceLayer service;

    public FlooringServiceImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("FlooringTestApplicationContext.xml");
        service = ctx.getBean("serviceLayer", FlooringServiceLayer.class);
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
     * Test of obtainFileName method, of class FlooringServiceImpl.
     */
    //@Test
    public void testObtainFileName() {

    }

    // @Test
    public void testObtainLocalDate() throws Exception {
        LocalDate newDate = LocalDate.now();
        newDate = service.obtainOrderDate(newDate);

        assertTrue(newDate.toString().equalsIgnoreCase("2017-06-20"));

    }

    @Test
    public void testRetrieveAllORdersByFileNameTOLIST1() throws Exception {
        LocalDate ourDate = LocalDate.of(2017, 05, 16);
        service.obtainOrderDate(ourDate);
        List<Order> orderList = service.retrieveAllOrdersByOrderDate2(ourDate);

        assertTrue(orderList.get(1).getGrandTotal().toString().equalsIgnoreCase("816.64"));

    }

    @Test
    public void testRetrieveAllORdersByFileNameTOLIST2() throws Exception {
        List<Order> orderList;
        LocalDate ourDate = LocalDate.of(2017, 12, 25);
        service.obtainOrderDate(ourDate);
        try {
            orderList = service.retrieveAllOrdersByOrderDate2(ourDate);
        } catch (FlooringOrderPersistenceException e) {
            orderList = null;
        }
          assertEquals(orderList, null);
    //*** THERE ARE NO ORDERS WITH THIS DATE ***
      }

    @Test
    public void testRetrieveOrderByOrderDate() throws Exception {
        LocalDate ourDate = LocalDate.of(2017, 05, 16);
        service.obtainOrderDate(ourDate);
        List<Order> ourFiles = service.retrieveAllOrdersByOrderDate2(ourDate);

        assertTrue(ourFiles.size() == 2);
    }

    @Test
    public void testRetrieveOrderByOrderId() throws Exception {
        LocalDate ourDate = LocalDate.of(2017, 05, 16);
        service.obtainOrderDate(ourDate);
        List<Order> ourFiles = service.retrieveAllOrdersByOrderDate2(ourDate);
    }

    @Test
    public void testRetrieveAllProdcutInfo() throws Exception {
        List<Product> prodList = service.retrieveAllProductInfo();

        assertTrue(prodList.size() == 4);
    }

    @Test
    public void testobtainProductInfoByMaterial() throws Exception {
        Product product = service.obtainProductInfoByMaterial("Wood");
        BigDecimal ourNumber = new BigDecimal("4.75");

        assertTrue(product.getLaborCostPerSqFt().equals(ourNumber));
    }

    @Test
    public void testRetrieveAllTaxInfo() throws Exception {
        List<Tax> taxList = service.retrieveAllTaxRates();

        assertTrue(taxList.size() == 3);
    }

    @Test
    public void testobtainTaxRateByState() throws Exception {
        Tax taxRate = service.obtainTaxRateByState("OH");
        double ourNumber = 6.25;

        assertTrue(taxRate.getTaxRate() == ourNumber);
    }

    @Test
    public void testSaveOrder() throws Exception {
        LocalDate ourDate = LocalDate.of(2017, 05, 16);
        service.obtainOrderDate(ourDate);
        List<Order> ourFiles = service.retrieveAllOrdersByOrderDate2(ourDate);

        LocalDate ourDate2 = LocalDate.of(2013, 06, 01);
        service.obtainOrderDate(ourDate2);
        ourFiles = service.retrieveAllOrdersByOrderDate2(ourDate);

        assertTrue(ourFiles.size() == 2);
        service.saveOrders();

        assertTrue(ourFiles.get(1).getCustomerLastName().equalsIgnoreCase("Swanson"));
    }

    @Test
    public void testValidationWithinCreateOrder1() throws Exception {
        LocalDate testDate = LocalDate.of(2017, 04, 02);
        service.obtainOrderDate(testDate);

        Order order1 = new Order(1);
        order1.setOrderDate(testDate);
        order1.getOrderId();
        order1.setCustomerLastName("Swanson");
        Tax tax1 = new Tax("OH", 6.25);
        order1.setTax(tax1);
        Product product1 = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
        order1.setProduct(product1);
        order1.setArea(100);
        order1.getLaborCostTotal();
        order1.getMaterialCostTotal();
        order1.getTaxTotal();
        order1.getGrandTotal();

        service.createNewOrder(order1);
    }

    @Test
    public void testValidationWithinCreateOrder2() throws Exception {
        LocalDate testDate = LocalDate.of(2017, 04, 02);
        service.obtainOrderDate(testDate);

        Order order1 = new Order(1);
        order1.setOrderDate(testDate);
        order1.getOrderId();
        order1.setCustomerLastName("");
        Tax tax1 = new Tax("OH", 6.25);
        order1.setTax(tax1);
        Product product1 = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
        order1.setProduct(product1);
        order1.setArea(100);
        order1.getLaborCostTotal();
        order1.getMaterialCostTotal();
        order1.getTaxTotal();
        order1.getGrandTotal();
        try {
            service.createNewOrder(order1);
            fail("Expected OrderDataValidationException ");

        } catch (OrderDataValidationException e) {

        }
    }

    @Test
    public void testValidationWithinCreateOrder3() throws Exception {
        LocalDate testDate = LocalDate.of(2017, 04, 02);
        service.obtainOrderDate(testDate);

        Order order1 = new Order(1);
        order1.setOrderDate(testDate);
        order1.getOrderId();
        order1.setCustomerLastName("Swanson");
        Tax tax1 = new Tax("OH", 6.25);
        order1.setTax(tax1);
        Product product1 = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
        order1.setProduct(product1);
        order1.setArea(-1);
        order1.getLaborCostTotal();
        order1.getMaterialCostTotal();
        order1.getTaxTotal();
        order1.getGrandTotal();
        try {
            service.createNewOrder(order1);
            fail("EXpected OrderDataValidationException ");

        } catch (OrderDataValidationException e) {

        }
    }

    @Test
    public void testCreateOrder() throws Exception {
        LocalDate testDate = LocalDate.of(2017, 04, 02);
        service.obtainOrderDate(testDate);
        
        Order order1 = new Order();
        order1.setOrderDate(testDate);
        order1.getOrderId();
        order1.setCustomerLastName("Swanson");
        Tax tax1 = new Tax("OH", 6.25);
        order1.setTax(tax1);
        Product product1 = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
        order1.setProduct(product1);
        order1.setArea(100);

         
        service.createNewOrder(order1);

        List<Order> ourTestOrders = service.retrieveAllOrdersByOrderDate2(testDate);
        Order order = service.obtainOrderById(ourTestOrders, 2);
        assertEquals(order.getOrderDate(), order1.getOrderDate());

    }

//below does not work with new validation desires...(kinda expected)
    @Test
    public void testRemoveOrder() throws Exception {
        LocalDate testDate = LocalDate.of(2017, 04, 02);
        service.obtainOrderDate(testDate);

        Order order1 = new Order();
        order1.setOrderDate(testDate);
        order1.getOrderId();
        order1.setCustomerLastName("Swanson");
        Tax tax1 = new Tax("OH", 6.25);
        order1.setTax(tax1);
        Product product1 = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
        order1.setProduct(product1);
        order1.setArea(100);
        order1.getLaborCostTotal();
        order1.getMaterialCostTotal();
        order1.getTaxTotal();
        order1.getGrandTotal();

        service.createNewOrder(order1);

        testDate = LocalDate.of(2017, 04, 02);
        service.obtainOrderDate(testDate);

        Order order2 = new Order();
        order2.setOrderDate(testDate);
        order2.getOrderId();
        order2.setCustomerLastName("Swanson");
        Tax tax2 = new Tax("PA", 6.75);
        order2.setTax(tax2);
        Product product2 = new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));
        order2.setProduct(product2);
        order2.setArea(50);
        order2.getLaborCostTotal();
        order2.getMaterialCostTotal();
        order2.getTaxTotal();
        order2.getGrandTotal();

        service.createNewOrder(order2);

        List<Order> ourTestOrders = service.retrieveAllOrdersByOrderDate2(testDate);

        assertEquals(ourTestOrders.size(), 2);

        Order orderToRemove = service.obtainOrderById(ourTestOrders, 2);

        service.removeOrder(orderToRemove);

        ourTestOrders = service.retrieveAllOrdersByOrderDate2(testDate);

        assertEquals(ourTestOrders.size(), 1);

    }

}
