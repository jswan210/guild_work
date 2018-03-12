/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringAuditDao;
import com.sg.flooringmastery.dao.FlooringOrderDao;
import com.sg.flooringmastery.dao.FlooringOrderPersistenceException;
import com.sg.flooringmastery.dao.FlooringProductDao;
import com.sg.flooringmastery.dao.FlooringTaxDao;
import com.sg.flooringmastery.dao.GlobalIdDao;
import com.sg.flooringmastery.dao.GlobalIdInfoPersistenceException;
import com.sg.flooringmastery.dao.ProductInfoPersistenceException;
import com.sg.flooringmastery.dao.TaxInfoPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jswan
 */
public class FlooringServiceImpl implements FlooringServiceLayer {

    private FlooringOrderDao orderDao;
    private FlooringTaxDao taxDao;
    private FlooringProductDao productDao;
    private GlobalIdDao globalIdDao;
    private List<Tax> taxFileInfo = new ArrayList<>();
    private List<Product> productFileInfo = new ArrayList<>();
    private FlooringAuditDao auditDao;

    public FlooringServiceImpl(FlooringOrderDao orderDao, FlooringTaxDao taxDao,
            FlooringProductDao productDao, GlobalIdDao globalIdDao, FlooringAuditDao auditDao) throws
            TaxInfoPersistenceException, ProductInfoPersistenceException {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
        this.globalIdDao = globalIdDao;
        this.auditDao = auditDao;
        taxFileInfo = this.retrieveAllTaxRates();
        productFileInfo = this.retrieveAllProductInfo();
    }

    @Override
    public int getNewGlobalIDNumber() throws GlobalIdInfoPersistenceException {
        return globalIdDao.getNewID();
    }

    @Override
    public List<Order> retrieveAllOrdersByOrderDate2(LocalDate dateOfOrders) throws FlooringOrderPersistenceException {
        List<Order> orderList = orderDao.retrieveAllOrdersByOrderDateToList(dateOfOrders);
        if (orderList.size() == 0) {
            throw new FlooringOrderPersistenceException(
                    "*** THERE ARE NO ORDERS WITH THIS DATE ***");
        } else {
            return orderList;
        }
    }

    @Override
    public Order obtainOrderById(List<Order> orders, int itemId) throws FlooringOrderPersistenceException,
            FlooringOrderNumberDoesNotExistException {
        if (orderDao.obtainOrderbyId(itemId) == null) {
            throw new FlooringOrderNumberDoesNotExistException(
                    "*** THIS ORDER DOES NOT EXIST ***");
        } else {
            return orderDao.obtainOrderbyId(itemId);
        }
    }

    @Override
    public LocalDate obtainOrderDate(LocalDate ld) {
        return orderDao.obtainOrderDate(ld);
    }

    @Override
    public void removeOrder(Order removeOrder) throws FlooringOrderPersistenceException {
        orderDao.removeOrder(removeOrder);
    }

    @Override
    public List<Product> retrieveAllProductInfo() throws ProductInfoPersistenceException {
        return productDao.retrieveAllProductInfo();
    }

    @Override
    public Product obtainProductInfoByMaterial(String material) throws
            ProductInfoPersistenceException,
            GivenProductMaterialIsInvalidException {
        validateProperMaterial(material);
        Product product = productDao.obtainProductInfoByMaterial(material);
        return product;
    }

    private void validateProperMaterial(String material) throws GivenProductMaterialIsInvalidException {
        List<Product> usersMaterialChoice = productFileInfo.stream()
                .filter(m -> m.getMaterial().equalsIgnoreCase(material))
                .collect(Collectors.toList());

        if (usersMaterialChoice.size() == 0) {
        throw new GivenProductMaterialIsInvalidException(" *** "
                + material.toUpperCase() + " WE DO NOT HAVE PRODUCT INFO FOR MATERIAL ENTERED***");
        }
    }

    @Override
    public List<Tax> retrieveAllTaxRates() throws TaxInfoPersistenceException {
        return taxDao.retrieveAllTaxRates();
    }

    @Override
    public Tax obtainTaxRateByState(String state) throws GivenStateIsInvalidException,
            TaxInfoPersistenceException {
        validateProperState(state);
        Tax tax = taxDao.obtainTaxRateByState(state);
        return tax;
    }

    private void validateProperState(String state) throws GivenStateIsInvalidException {
        List<Tax> usersStateChoice = taxFileInfo.stream()
                .filter(s -> s.getState().equalsIgnoreCase(state))
                .collect(Collectors.toList());

        if (usersStateChoice.size() == 0) {
            throw new GivenStateIsInvalidException(" *** " + state.toUpperCase() + " WE DO NOT HAVE TAX INFO FOR THAT STATE***");
        }
    }

    @Override
    public void saveOrders() throws FlooringOrderPersistenceException {
        orderDao.saveOrders();
    }

    @Override
    public void createNewOrder(Order order)
            throws FlooringOrderPersistenceException,
            TaxInfoPersistenceException,
            OrderDataValidationException {

        try {
            validateOrderData(order);
            order.setOrderId(this.getNewGlobalIDNumber());
            this.retrieveAllOrdersByOrderDate2(order.getOrderDate());
            orderDao.createNewOrder(order);
        
        } catch (GlobalIdInfoPersistenceException e) {
            System.out.println("Could Not Generate an appropriate Order ID Number");
        } catch (FlooringOrderPersistenceException e) {
            orderDao.createNewOrder(order);
        }
    }

    @Override
    public Order updateOrder(Order order) throws FlooringOrderPersistenceException,
            OrderDataValidationException {
        validateOrderData(order);
        this.retrieveAllOrdersByOrderDate2(order.getOrderDate());
        return orderDao.updateOrder(order);

    }

    private void validateOrderData(Order order) throws OrderDataValidationException {

        if (order.getCustomerLastName() == null
                || order.getCustomerLastName().isEmpty()
                || order.getTax().getState().isEmpty()
                || order.getTax() == null
                || order.getTax().getTaxRate() == 0
                || order.getProduct() == null
                || order.getProduct().getMaterial().isEmpty()
                || order.getProduct().getMaterialCostPerSqFt() == null
                || order.getProduct().getMaterialCostPerSqFt().equals(BigDecimal.ZERO)
                || order.getProduct().getLaborCostPerSqFt() == null
                || order.getProduct().getLaborCostPerSqFt().equals(BigDecimal.ZERO)
                || order.getArea() < 0
                || order.getMaterialCostTotal() == null
                || order.getMaterialCostTotal().equals(BigDecimal.ZERO)
                || order.getLaborCostTotal() == null
                || order.getLaborCostTotal().equals(BigDecimal.ZERO)
                || order.getTaxTotal() == null
                || order.getTaxTotal().equals(BigDecimal.ZERO)
                || order.getGrandTotal() == null
                || order.getGrandTotal().equals(BigDecimal.ZERO)) {
            throw new OrderDataValidationException(
                    "ERROR: All fields Name, State, Material, and Area have not been entered.");
        }
    }
}
