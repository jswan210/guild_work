/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dao.FlooringOrderPersistenceException;
import com.sg.flooringmastery.dao.GlobalIdInfoPersistenceException;
import com.sg.flooringmastery.dao.ProductInfoPersistenceException;
import com.sg.flooringmastery.dao.TaxInfoPersistenceException;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.time.LocalDate;
import java.util.List;


/**
 *
 * @author jswan
 */
public interface FlooringServiceLayer {

    LocalDate obtainOrderDate(LocalDate ld);

    List<Order> retrieveAllOrdersByOrderDate2(LocalDate dateOfOrders) throws FlooringOrderPersistenceException;

    Product obtainProductInfoByMaterial(String material) throws GivenProductMaterialIsInvalidException,
            ProductInfoPersistenceException;

    List<Product> retrieveAllProductInfo() throws ProductInfoPersistenceException;

    List<Tax> retrieveAllTaxRates() throws TaxInfoPersistenceException;

    Tax obtainTaxRateByState(String state) throws GivenStateIsInvalidException,
            TaxInfoPersistenceException;

    Order obtainOrderById(List<Order> orders, int itemId) throws FlooringOrderPersistenceException,
            FlooringOrderNumberDoesNotExistException;

    void saveOrders() throws FlooringOrderPersistenceException;

    void removeOrder(Order removeOrder) throws FlooringOrderPersistenceException;

    void createNewOrder(Order order) throws FlooringOrderPersistenceException,
            TaxInfoPersistenceException,
            OrderDataValidationException;

    Order updateOrder(Order order) throws FlooringOrderPersistenceException,
            OrderDataValidationException;

    int getNewGlobalIDNumber() throws GlobalIdInfoPersistenceException;
}
      
    
   


