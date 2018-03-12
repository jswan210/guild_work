/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author jswan
 */
public interface FlooringOrderDao {

    /**
     * Puts an order into a nested hashmap using the information in the Order
     * passed into the method. returns the Order object given after
     *
     * @param order
     * @return Order
     * @throws FlooringOrderPersistenceException
     */
    Order createNewOrder(Order order) throws FlooringOrderPersistenceException;

    /**
     * requires a LocalDate object and passes it into the class level variable
     * for later use. and returns the LocalDate object
     *
     * @param Order order
     * @return Order
     */
    LocalDate obtainOrderDate(LocalDate orderDate);

    /**
     * requires a LocalDate object and searches the file system for the
     * orderDate given and returns a list of all the Orders in that file
     *
     * @param LocalDate orderDate
     * @return List<Order>
     */
    List<Order> retrieveAllOrdersByOrderDateToList(LocalDate orderDate) throws FlooringOrderPersistenceException;

    /**
     * takes an integer itemId and returns the Order associated with that number
     * from the List<Order>
     * if the orderId does not match
     *
     * @param int itemId
     * @return Order
     *
     */
    Order obtainOrderbyId(int itemId) throws FlooringOrderPersistenceException;

    /**
     * takes an Order and removes that order from the Nested Hashmap
     *
     * @param Order
     */
    void removeOrder(Order removeOrder) throws FlooringOrderPersistenceException;

    /**
     * takes an Order and returns and updates it into the nested Hashmap
       *
     * @param Order
     * @throws FlooringOrderPersistenceException
     */
    Order updateOrder(Order order) throws FlooringOrderPersistenceException;

    /**
     * saves all of the orders in the nested hashmap by OrderDate
     *
     * @throws FlooringOrderPersistenceException
     */
    void saveOrders() throws FlooringOrderPersistenceException;

}
