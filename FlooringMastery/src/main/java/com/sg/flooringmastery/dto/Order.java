/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/**
 * order number, customer name, state, tax rate, product type, area, cost per
 * square foot, labor cost per square foot, material cost, labor cost, tax, and
 * total.
 *
 * @author jswan
 */
public class Order {
    private LocalDate orderDate;
    private int orderId;
    private String customerLastName;
    private Tax tax;
    private Product product;
    private double area;


    public Order() {
    }

    public Order( Order duplicateOrder){
        Order dup = new Order();
        this.orderDate = duplicateOrder.getOrderDate();
        dup.setOrderDate(orderDate);
        this.orderId= duplicateOrder.getOrderId();
        dup.setOrderId(orderId);
        this.customerLastName = duplicateOrder.getCustomerLastName();
        dup.setCustomerLastName(customerLastName);
        this.area=duplicateOrder.getArea();
        dup.setArea(area);
        this.product = duplicateOrder.getProduct();
        dup.setProduct(product);
        this.tax = duplicateOrder.getTax();
        dup.setTax(tax);

        
    }

    public Order(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public BigDecimal getMaterialCostTotal() {
        return product.getMaterialCostPerSqFt()
                .multiply(new BigDecimal(Double.toString(area)))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getLaborCostTotal() {
        return product.getLaborCostPerSqFt()
                .multiply(new BigDecimal(Double.toString(area)))
                .setScale(2, RoundingMode.HALF_UP);
    }
    public BigDecimal getTaxTotal() {
        return ((getMaterialCostTotal().add(getLaborCostTotal())))
                .multiply(new BigDecimal(Double.toString(tax.getTaxRate())).divide(new BigDecimal("100")))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getGrandTotal() {
        return getTaxTotal().add(getLaborCostTotal()).add(getMaterialCostTotal());
    }

    @Override
    public String toString() {
        return "Order{" + "orderDate=" + orderDate + ", orderId=" + orderId + ", customerLastName=" + customerLastName + ", tax=" + tax + ", product=" + product + ", area=" + area + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.orderDate);
        hash = 79 * hash + this.orderId;
        hash = 79 * hash + Objects.hashCode(this.customerLastName);
        hash = 79 * hash + Objects.hashCode(this.tax);
        hash = 79 * hash + Objects.hashCode(this.product);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.area) ^ (Double.doubleToLongBits(this.area) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderId != other.orderId) {
            return false;
        }
        if (Double.doubleToLongBits(this.area) != Double.doubleToLongBits(other.area)) {
            return false;
        }
        if (!Objects.equals(this.customerLastName, other.customerLastName)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return true;
    }

}
