/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class FlooringOrderDaoTest {

    FlooringOrderDao orderDao = new FlooringOrderDaoFileImpl();
    private Map<LocalDate, HashMap<Integer, Order>> ordersTest = new HashMap();

    public FlooringOrderDaoTest() {
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
     * Test of createNewOrder method, of class FlooringOrderDao.
     */
    @Test
    public void testOrderSettingOfMaterialLaborTaxGrandTotals() throws Exception {
        LocalDate fileName = LocalDate.of(2017, 05, 16);
        orderDao.obtainOrderDate(fileName);
        Order order1 = new Order(1);
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
        ordersTest.put(fileName, new HashMap() {
            {
                put(order1.getOrderId(), order1);
            }
        });

        assertEquals(order1.getMaterialCostTotal(), new BigDecimal("225.00"));
        assertEquals(order1.getLaborCostTotal(), new BigDecimal("210.00"));
        assertEquals(order1.getTaxTotal(), new BigDecimal("27.19"));
        assertEquals(order1.getGrandTotal(), new BigDecimal("462.19"));
    }

    /**
     * Test of this retrieves all orders in a specific file, the LocalDate entity 
     * is taken and formatted into a string in the loadFile() method in the orderDao
     * the loadFile in turn finds the file and puts all the
     * information into a nested     * hashmap...and returned...
     */
    @Test
    public void testRetrieveAllOrdersByFileName() throws Exception {
        LocalDate fileName = LocalDate.of(2013, 06, 01);
        orderDao.obtainOrderDate(fileName);
        List<Order> orderList = orderDao.retrieveAllOrdersByOrderDateToList(fileName);

        assertEquals(1, orderList.size());
    }

    /**
     * Test of Order By ID: this will take does what the 
     * 'testRetrieveAllORdersBYFileName', so a Nested Hashmap that is returned from
     * the orderDaos retrieveAllOrdersByFileName, and then pulls just the Order 
     * we want by way of Order ID number...
     */
    @Test
    public void testOrderById() throws Exception {

        LocalDate testfileName = LocalDate.of(2017, 05, 16);

        orderDao.retrieveAllOrdersByOrderDateToList(testfileName);
        Order ourChosenOrder = orderDao.obtainOrderbyId(2);

        assertEquals("Carpet", ourChosenOrder.getProduct().getMaterial());

    }

    @Test
    public void testRemoveOrder() throws Exception {

        LocalDate testfileName = LocalDate.of(2017, 05, 16);

        orderDao.retrieveAllOrdersByOrderDateToList(testfileName);
        Order ourChosenOrder = orderDao.obtainOrderbyId(2);

        orderDao.removeOrder(ourChosenOrder);

        assertNull(orderDao.obtainOrderbyId(2));
    }

    /**
     * Test of updateORder method, of class FlooringOrderDao.
     */
    @Test
    public void testUpdateOrder1() throws Exception {
        LocalDate testfileName = LocalDate.of(2017, 05, 16);
        orderDao.obtainOrderDate(testfileName);
        List<Order> ordersTest = orderDao.retrieveAllOrdersByOrderDateToList(testfileName);
        Order ourChosenOrder = orderDao.obtainOrderbyId(2);

        ourChosenOrder.setCustomerLastName("BOBBERTSON");

        Order updatedOrder = orderDao.updateOrder(ourChosenOrder);

        assertTrue("BOBBERTSON".equalsIgnoreCase(updatedOrder.getCustomerLastName()));
    }

    @Test
    public void testUpdateOrder2() throws Exception {
        LocalDate testfileName = LocalDate.of(2017, 05, 16);
        orderDao.obtainOrderDate(testfileName);
        orderDao.retrieveAllOrdersByOrderDateToList(testfileName);
        Order ourChosenOrder = orderDao.obtainOrderbyId(2);
        ourChosenOrder.setCustomerLastName("Pollock");

        Order updatedOrder = orderDao.updateOrder(ourChosenOrder);

        assertTrue("Pollock".equalsIgnoreCase(updatedOrder.getCustomerLastName()));
    }

    @Test
    public void testUpdateOrder3() throws Exception {
        LocalDate testfileName = LocalDate.of(2017, 05, 16);
        orderDao.obtainOrderDate(testfileName);
        orderDao.retrieveAllOrdersByOrderDateToList(testfileName);
        Order ourChosenOrder = orderDao.obtainOrderbyId(3);
        LocalDate testNewFileName = LocalDate.of(2017, 04, 07);

        orderDao.obtainOrderDate(testNewFileName);

        ourChosenOrder.setCustomerLastName("Pollock");
        Order updatedOrder = orderDao.updateOrder(ourChosenOrder);

        assertTrue("Pollock".equalsIgnoreCase(updatedOrder.getCustomerLastName()));

    }

    @Test
    public void testCreatNewOrder1() throws Exception {
        LocalDate date = LocalDate.now();
        Order order1 = new Order();
        order1.setOrderDate(date);
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

        Order createNewOrder = orderDao.createNewOrder(order1);
        assertEquals(createNewOrder.getProduct().getMaterial(), order1.getProduct().getMaterial());

    }

}
