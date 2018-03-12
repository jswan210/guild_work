/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author jswan
 */
public class FlooringOrderDaoFileImpl implements FlooringOrderDao {

    public static final String DELIMITER = ",";
    private LocalDate dateOfOrder;
    private Map<LocalDate, HashMap<Integer, Order>> ordersByDate = new HashMap<>();
    private List<Order> orderList = new ArrayList<>();


    static final int ORDER_ID = 0;
    static final int NAME = 1;
    static final int STATE = 2;
    static final int TAX_RATE = 3;
    static final int MATERIAL = 4;
    static final int AREA = 5;
    static final int MATERIAL_COST_PER_SQ_FT = 6;
    static final int LABOR_COST_PER_SQ_FT = 7;

    @Override
    public List<Order> retrieveAllOrdersByOrderDateToList(LocalDate orderDate) throws FlooringOrderPersistenceException {
        dateOfOrder = orderDate;
        orderList.removeAll(orderList);
        Set<LocalDate> dates = new HashSet(ordersByDate.keySet());

        if (dates.contains(orderDate) == false) {
            try {
                loadOrders();
                return orderList = ordersByDate.get(orderDate).values().stream().collect(Collectors.toList());
            } catch (FlooringOrderPersistenceException e) {
                return orderList;
            }
        }
        return orderList = ordersByDate.get(orderDate).values().stream().collect(Collectors.toList());
    }
      
    @Override
    public LocalDate obtainOrderDate(LocalDate orderDate) {
        return this.dateOfOrder = orderDate;
    }

    @Override
    public Order obtainOrderbyId(int orderId) throws FlooringOrderPersistenceException {
        Order orderById = ordersByDate.get(dateOfOrder).get(orderId);
        return orderById;

    }

    @Override
    public Order createNewOrder(Order newOrder) throws FlooringOrderPersistenceException {
        dateOfOrder = newOrder.getOrderDate();

        if (ordersByDate.get(newOrder.getOrderDate()) == null) {

            ordersByDate.put(newOrder.getOrderDate(), new HashMap() {
                {
                    put(newOrder.getOrderId(), newOrder);
                }
            });
        } else {
            ordersByDate.get(newOrder.getOrderDate()).put(newOrder.getOrderId(), newOrder);
        }

        return newOrder;
    }
    
      @Override
    public void saveOrders() throws FlooringOrderPersistenceException {
          writeOrders();
    }

    @Override
    public Order updateOrder(Order updateOrder) throws FlooringOrderPersistenceException {
        if (ordersByDate.get(updateOrder.getOrderDate()) == null) {

            ordersByDate.put(updateOrder.getOrderDate(), new HashMap() {
                {
                    put(updateOrder.getOrderId(), updateOrder);
                }
            });
        } else {
            ordersByDate.get(updateOrder.getOrderDate()).put(updateOrder.getOrderId(), updateOrder);
        }

        return updateOrder;

    }

    @Override
    public void removeOrder(Order removeOrder) throws FlooringOrderPersistenceException {
        ordersByDate.get(removeOrder.getOrderDate()).remove(removeOrder.getOrderId());

    }

    private PrintWriter searchForFile() throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter("Orders_" + dateOfOrder.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt"));
        return out;
    }

    private void loadOrders() throws FlooringOrderPersistenceException {
        Scanner scanner;

        String stringFileName = "Orders_" + dateOfOrder.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt";
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(stringFileName)));
           
        } catch (FileNotFoundException e) {
            throw new FlooringOrderPersistenceException(
                    "**** ORDER DOES NOT SEEM TO EXIST ****\n\n", e);
        }

        String currentLine;

        String[] currentTokens;
        scanner.nextLine();

        while (scanner.hasNextLine()) {

            Order currentOrder = new Order();
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            currentOrder.setOrderId(Integer.parseInt(currentTokens[ORDER_ID]));
            currentOrder.setCustomerLastName(currentTokens[NAME].replace("::", ","));
            currentOrder.setTax(new Tax(currentTokens[STATE], Double.parseDouble(currentTokens[TAX_RATE])));
            currentOrder.setProduct(new Product(currentTokens[MATERIAL],
                    new BigDecimal(currentTokens[MATERIAL_COST_PER_SQ_FT]),
                    new BigDecimal(currentTokens[LABOR_COST_PER_SQ_FT])));
            currentOrder.setArea(Double.parseDouble(currentTokens[AREA]));
            currentOrder.getMaterialCostTotal();
            currentOrder.getLaborCostTotal();
            currentOrder.getTaxTotal();
            currentOrder.getGrandTotal();
            currentOrder.setOrderDate(dateOfOrder);

            if (ordersByDate.get(dateOfOrder) != null) {
                ordersByDate.get(dateOfOrder).put(currentOrder.getOrderId(), currentOrder);
            } else {
                ordersByDate.put(dateOfOrder, new HashMap<Integer, Order>() {
                {
                    put(currentOrder.getOrderId(), currentOrder);
                }
                });
            }
        }
        
        scanner.close();
    }

    private void writeOrders() throws FlooringOrderPersistenceException {
        Set<LocalDate> searchByFile = ordersByDate.keySet();

          for (LocalDate currentDate : searchByFile) {
              List<Order> ordersList = ordersByDate.get(currentDate).values().stream().collect(Collectors.toList());
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter("Orders_" + currentDate.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt"));
        } catch (IOException e) {
            throw new FlooringOrderPersistenceException(
                    "Could not save order data.", e);
        }
        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

              for (Order currentOrder : ordersList) {

            out.println(currentOrder.getOrderId() + DELIMITER
                    + currentOrder.getCustomerLastName().substring(0, 1).replace(",", "::")
                            .toUpperCase() + currentOrder.getCustomerLastName()
                            .substring(1).replace(",", "::") + DELIMITER
                    + currentOrder.getTax().getState().toUpperCase() + DELIMITER
                    + currentOrder.getTax().getTaxRate() + DELIMITER
                    + currentOrder.getProduct().getMaterial().substring(0, 1)
                            .toUpperCase() + currentOrder.getProduct().getMaterial()
                            .substring(1) + DELIMITER
                    + currentOrder.getArea() + DELIMITER
                    + currentOrder.getProduct().getMaterialCostPerSqFt() + DELIMITER
                    + currentOrder.getProduct().getLaborCostPerSqFt() + DELIMITER
                    + currentOrder.getMaterialCostTotal() + DELIMITER
                    + currentOrder.getLaborCostTotal() + DELIMITER
                    + currentOrder.getTaxTotal() + DELIMITER
                    + currentOrder.getGrandTotal());

            out.flush();
        }
        out.close();
    }
    }
}

