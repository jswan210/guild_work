/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author jswan
 */
public class FlooringView {
    static final String REMOVE = "Are you sure you want to remove this order? YES or NO";
    static final String SAVE = "Are you sure you want to save your current progress? YES or NO";
    static final String UPDATE = "Do you want to continue to update this order? YES or NO";
    static final String CREATE = "Would you like to ADD this order? YES or NO";

    public FlooringView(UserIO io) {
        this.io = io;
    }
    private UserIO io;

    public int printMenuAndGetSelection() {
        io.print("x x x x x x x x x x x x x x x x x x x x ");
        io.print("x");
        io.print("x  << FLOORING PROGRAM >>");
        io.print("x    1. Display Orders");
        io.print("x    2. Add an Order");
        io.print("x    3. Edit an Order");
        io.print("x    4. Remove an Order");
        io.print("x    5. Save Current Work");
        io.print("x    6. Quit");
        io.print("x");
        io.print("x x x x x x x x x x x x x x x x x x x x ");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayExitBanner() {
        io.print("Exiting Order View/Edit/Create, Thank you...GoodBye.");
    }

    public void displayDisplayOrderBanner() {
        io.print("xxxxxxxxx DISPLAY AN ORDER xxxxxxxx");
    }

    public LocalDate askUserForChangeOfDateOfOrder(Order order) {
        boolean dateTimeCheck = false;
        LocalDate usersDate = null;
        while (dateTimeCheck == false) {
            try {
                usersDate = io.readLocalDateAllowsNoInputFromUser("Enter new Date of Order [mmddyyyy] or press ENTER: ");
                if (usersDate == null) {
                    usersDate = order.getOrderDate();
                }
                dateTimeCheck = true;
            } catch (DateTimeException e) {
                io.print("<><><> INVALID DATE <><><>");
                io.print("Please Enter A Valid Date");
                dateTimeCheck = false;
            }
        }
        return usersDate;
    }

    public LocalDate askUserForDateOfOrder() {
        boolean dateTimeCheck = false;
        LocalDate usersDate = null;
        while (dateTimeCheck == false) {
            try {
                usersDate = io.readLocalDate("Enter Date of Order [MMDDYYYY]: ");
                dateTimeCheck = true;
            } catch (DateTimeException e) {
                io.print("<><><> INVALID DATE <><><>");
                io.print("Please Enter A Valid Date");
                dateTimeCheck = false;
            }
        }
        return usersDate;
    }

    public void displayErrorMessage(String message) {
        io.print("<><><> ERROR <><><>");
        io.print(message);
    }

    public int askUserForOrderID() {
        return io.readInt("Select Order ID number: ");
    }

    public void displayOrderInfo(Order order) {
        io.print("xxxxxxxx    ORDER    xxxxxxxx\n");
        io.print("Order Id Number: " + order.getOrderId());
        io.print("Name: " + order.getCustomerLastName().replace("::", ","));
        io.print("State: " + order.getTax().getState() + "\t\t\t\tTax Rate: "
                + order.getTax().getTaxRate());
        io.print("Material: " + order.getProduct().getMaterial());
        io.print("Area [sqft]: " + order.getArea());
        io.print("Material Cost [per sqft]: $" + order.getProduct().getMaterialCostPerSqFt()
                + "\t\tLabor Cost [per sqft]: $" + order.getProduct().getLaborCostPerSqFt());
        io.print("Total Material Cost: $" + order.getMaterialCostTotal());
        io.print("Total Labor Cost: $" + order.getLaborCostTotal());
        io.print("Tax: $" + order.getTaxTotal());
        io.print("Grand Total: $" + order.getGrandTotal());

        io.print("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
    }

    public void displayToBeCreatedOrderInfo(Order order) {
        io.print("xxxxxxxx    ORDER    xxxxxxxx\n");
        io.print("Name: " + order.getCustomerLastName().replace("::", ","));
        io.print("State: " + order.getTax().getState() + "\t\t\t\tTax Rate: "
                + order.getTax().getTaxRate());
        io.print("Material: " + order.getProduct().getMaterial());
        io.print("Area [sqft]: " + order.getArea());
        io.print("Material Cost [per sqft]: $" + order.getProduct().getMaterialCostPerSqFt()
                + "\t\tLabor Cost [per sqft]: $" + order.getProduct().getLaborCostPerSqFt());
        io.print("Total Material Cost: $" + order.getMaterialCostTotal());
        io.print("Total Labor Cost: $" + order.getLaborCostTotal());
        io.print("Tax: $" + order.getTaxTotal());
        io.print("Grand Total: $" + order.getGrandTotal());

        io.print("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");

    }

    public void displayAvailableStatesFromTaxFile(List<Tax> taxFileInfo) {
        io.print("Please select State: ");
        taxFileInfo.forEach((fileTax) -> {
            io.print(fileTax.getState());
        });
    }

    public void displayAvailableMaterialFromProductFile(List<Product> productFileInfo) {
        io.print("Please select one of the following Material Options: ");
        productFileInfo.forEach((productMaterial) -> {
            io.print(productMaterial.getMaterial());
        });
    }

    public void displaySuccessfulCreationOfOrder() {
        io.print("xxxxxxxxx ORDER CREATED SUCCESSFULLY xxxxxxxxx\n");
    }

    public void bannerUpdateOrder() {
        io.print("xxxxxxxxx UPDATE ORDER xxxxxxxxx");
    }

    public void bannerSuccessfulUpdateOfOrder() {
        io.print("xxxxxxxxx ORDER UPDATED SUCCESSFULLY xxxxxxxxx");
    }
    public void bannerAddOrderBanner() {
        io.print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        io.print("****    CREATE ORDER    ****");
    }

    public void displayListOfAvailableOrderIdsFromOrderDateList(List<Order> orderList) {
        io.print("Order IDs Available: ");
        orderList.stream()
                .sorted((id1, id2) -> Integer.compare(id1.getOrderId(),
                id2.getOrderId()))
                .forEach(id -> io.print(String.valueOf(id.getOrderId())));

    }

    public Order createNameForOrder(Order order) {
        boolean emptyName = true;
        while (emptyName) {
            String name = io.readString("Enter Customers Last Name: ");
            if (name.isEmpty()) {
                emptyName = true;
            } else {
                name = name.replace(",", "::");
                order.setCustomerLastName(name);
                return order;
            }
        }
        return order;
    }

    public String getUpdateStateOfOrder(Order order) {
        String state = io.readString("Enter new State or press ENTER: ");
        if (state.isEmpty()) {
            return order.getTax().getState();
        }
        return state;
    }

    public String getStateOfOrder() {
        String state = io.readString("Enter State: ");
        return state;
    }

    public String getUpdateMaterialOfOrder(Order order) {
        String material = io.readString("Enter new Material or press ENTER: ");

        if (material.equalsIgnoreCase("")) {
            return order.getProduct().getMaterial();
        } else {
            return material;
        }
    }

    public String getMaterialOfOrder() {
        String material = io.readString("Enter Material to be used: ");
        return material;
    }

    public Order getAreaForOrder(Order order) {
        double area = io.readPostiveDouble("Enter Total Area of the room to be finished: ");
        order.setArea(area);
        return order;
    }

    public Order getUpdatedAreaForOrder(Order order) {
        double area = io.readDoubleIfEmptyReturnsZero("Update Area On Order or press ENTER");
        if (area < 0) {
            return order;
        } else {
            order.setArea(area);
        }
        return order;
    }


    public void bannerRemoveAnOrder() {
        io.print("xxxxxxxx REMOVE AN ORDER xxxxxxxxx");
    }

    public boolean displayConfirmRemovalOfOrder() {
        boolean answerToQuestion = io.readBoolean(REMOVE, "Yes".toUpperCase(), "No".toUpperCase());
        return answerToQuestion;
    }

    public void bannerSucessfullyRemovedAnOrder() {
        io.print("xxxxxxxxx YOUR ORDER HAS BEEN REMOVED xxxxxxxxx");
    }

    public void bannerOrderRemovalRequestWithdrawn() {
        io.print("xxxxxxxx REMOVAL REQUEST WITHDRAWN xxxxxxxxx");
    }

    public void bannerSaveWork() {
        io.print("xxxxxxxxx SAVE YOUR PROGRESS xxxxxxxxx");
    }

    public boolean displayAskUserToSaveWork() {
        boolean answerToQuestion = io.readBoolean(SAVE, "Yes".toUpperCase(), "No".toUpperCase());
        return answerToQuestion;
    }

    public void displaySuccessfulSaveOfProgress() {
        io.print("xxxxxxxxx ALL CHANGES SAVED xxxxxxxxx");
    }

    public void displayWorkNotSaved() {
        io.print("xxxxxxxx YOUR WORK WAS NOT SAVED xxxxxxxxx");
    }

    public boolean displayConfirmUpdateOfOrder() {
        boolean answerToQuestion = io.readBoolean(UPDATE, "Yes".toUpperCase(), "No".toUpperCase());
        return answerToQuestion;
    }

    public void displayMessage(String message) {
        io.print(message);
    }
//ITS NOT WWHITE SPACE THAT IS THE ISSUE ITS THE CommA!!!!
    public Order updateName(Order ourOrder) {

        String newName = io.readString("Enter new Customer Name or press ENTER: ");
        if (!newName.equalsIgnoreCase("")) {
            ourOrder.setCustomerLastName(newName.replace(",", "::"));
        }
        return ourOrder;
    }

    public void displayOrderCreationActionCanceled() {
        io.print("xxxxxxxxx ORDER CANCELED xxxxxxxxx\n");
    }

    public void askUserToPressEnterToContinue() {
        io.readString("Press Enter To Continue\n");
    }

    public boolean askUserIfOrderIsToBeCreated() {
        boolean answerToQuestion = io.readBoolean(CREATE, "Yes".toUpperCase(), "No".toUpperCase());
        return answerToQuestion;
    }
}

