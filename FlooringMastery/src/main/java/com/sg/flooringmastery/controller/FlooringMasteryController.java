/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;


import com.sg.flooringmastery.dao.FlooringOrderPersistenceException;
import com.sg.flooringmastery.dao.GlobalIdInfoPersistenceException;
import com.sg.flooringmastery.dao.ProductInfoPersistenceException;
import com.sg.flooringmastery.dao.TaxInfoPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.service.FlooringOrderNumberDoesNotExistException;
import com.sg.flooringmastery.service.FlooringServiceLayer;
import com.sg.flooringmastery.service.GivenProductMaterialIsInvalidException;
import com.sg.flooringmastery.service.GivenStateIsInvalidException;
import com.sg.flooringmastery.service.OrderDataValidationException;
import com.sg.flooringmastery.ui.FlooringView;
import com.sg.flooringmastery.ui.UserIO;
import com.sg.flooringmastery.ui.UserIOConsoleImpl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 *
 * @author jswan
 */
public class FlooringMasteryController {

    public FlooringMasteryController(FlooringServiceLayer service, FlooringView view) {
        this.service = service;
        this.view = view;
    }

    private FlooringView view;
    private FlooringServiceLayer service;
    private UserIO io = new UserIOConsoleImpl();

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addAnOrder();
                        break;
                    case 3:
                        editAnOrder();
                        break;
                    case 4:
                        removeAnOrder();
                        break;
                    case 5:
                        saveWork();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                        break;
                }
            }
            exitMessage();

        } catch (FlooringOrderPersistenceException
                | ProductInfoPersistenceException | TaxInfoPersistenceException | OrderDataValidationException
                | GlobalIdInfoPersistenceException e) {

            view.displayErrorMessage(e.getMessage());
        }
    } 

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void displayOrders() {
        view.displayDisplayOrderBanner();
        try {
            List<Order> orderList = service.retrieveAllOrdersByOrderDate2(view.askUserForDateOfOrder());
            view.displayListOfAvailableOrderIdsFromOrderDateList(orderList);
            int orderId = view.askUserForOrderID();
            view.displayOrderInfo(service.obtainOrderById(orderList, orderId));
            view.askUserToPressEnterToContinue();

        } catch (FlooringOrderPersistenceException | FlooringOrderNumberDoesNotExistException e) {
            view.displayErrorMessage(e.getMessage());
            view.askUserToPressEnterToContinue();
        }
    }

    private void addAnOrder() throws FlooringOrderPersistenceException,
            TaxInfoPersistenceException,
            ProductInfoPersistenceException,
            OrderDataValidationException,
            GlobalIdInfoPersistenceException {
        view.bannerAddOrderBanner();
        List<Tax> taxFileInfo = service.retrieveAllTaxRates();
        List<Product> productFileInfo = service.retrieveAllProductInfo();
        LocalDate newOrderDate = LocalDate.now();

        Order newOrder = new Order();
        newOrder.setOrderDate(newOrderDate);
        view.createNameForOrder(newOrder);
        boolean validInput = false;
        while (!validInput) {
            try {
                view.displayAvailableStatesFromTaxFile(taxFileInfo);
                newOrder.setTax(service.obtainTaxRateByState(view.getStateOfOrder()));
                validInput = true;
            } catch (GivenStateIsInvalidException e) {
                validInput = false;
                view.displayErrorMessage(e.getMessage());
            }
        }
        validInput = false;
        while (!validInput) {
            try {
                view.displayAvailableMaterialFromProductFile(productFileInfo);
                newOrder.setProduct(service.obtainProductInfoByMaterial(view.getMaterialOfOrder()));
                validInput = true;
            } catch (GivenProductMaterialIsInvalidException e) {
                validInput = false;
                view.displayErrorMessage(e.getMessage());
            }
        }
        view.getAreaForOrder(newOrder);
        view.displayToBeCreatedOrderInfo(newOrder);

        boolean commitOrder = false;
        commitOrder = view.askUserIfOrderIsToBeCreated();
        if (commitOrder == true) {
            service.createNewOrder(newOrder);
            view.displayOrderInfo(newOrder);
            view.displaySuccessfulCreationOfOrder();
            view.askUserToPressEnterToContinue();
        } else {
            view.displayOrderCreationActionCanceled();
            view.askUserToPressEnterToContinue();
        }
    }

    private void removeAnOrder() {
        try {
            view.bannerRemoveAnOrder();
            List<Order> orderList = service.retrieveAllOrdersByOrderDate2(view.askUserForDateOfOrder());
            view.displayListOfAvailableOrderIdsFromOrderDateList(orderList);
            int orderId = view.askUserForOrderID();
            Order ourOrder = service.obtainOrderById(orderList, orderId);
            view.displayOrderInfo(ourOrder);
            boolean orderRemovalConfirmation = false;
            if (orderRemovalConfirmation = view.displayConfirmRemovalOfOrder() == true) {
                service.removeOrder(ourOrder);
                view.bannerSucessfullyRemovedAnOrder();
                view.askUserToPressEnterToContinue();
            } else {
                view.bannerOrderRemovalRequestWithdrawn();
                view.askUserToPressEnterToContinue();
            }
        } catch (FlooringOrderPersistenceException | FlooringOrderNumberDoesNotExistException e) {
            view.displayErrorMessage(e.getMessage());
            view.askUserToPressEnterToContinue();
        }
    }

    private void saveWork() throws FlooringOrderPersistenceException {
        view.bannerSaveWork();
        boolean saveOurWork;
        if (saveOurWork = view.displayAskUserToSaveWork() == true) {
            service.saveOrders();
            view.displaySuccessfulSaveOfProgress();
        } else {
            view.displayWorkNotSaved();
        }
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void editAnOrder() throws TaxInfoPersistenceException,
            ProductInfoPersistenceException,
            OrderDataValidationException {
        view.bannerUpdateOrder();

        try {
            LocalDate aDate = view.askUserForDateOfOrder();
            List<Order> orderList = service.retrieveAllOrdersByOrderDate2(aDate);
            view.displayListOfAvailableOrderIdsFromOrderDateList(orderList);
            int orderId = view.askUserForOrderID();

            Order ourOrder = service.obtainOrderById(orderList, orderId);
            Order originalOrder;
            originalOrder = new Order(ourOrder);
            view.displayOrderInfo(ourOrder);

            if (view.displayConfirmUpdateOfOrder()) {

//ask to change DATE
                view.displayMessage("Current Date: " + ourOrder.getOrderDate()
                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                ourOrder.setOrderDate(view.askUserForChangeOfDateOfOrder(ourOrder));

//ask to change NAME
                view.displayMessage("Current Name: " + ourOrder.getCustomerLastName().replace("::", ","));
                view.updateName(ourOrder);

//Ask to change STATE
                boolean validInput = false;
                while (!validInput) {
                    try {
                        view.displayMessage("Current State: " + ourOrder.getTax().getState());
                        view.displayAvailableStatesFromTaxFile(service.retrieveAllTaxRates());
                        ourOrder.setTax(service.obtainTaxRateByState(view.getUpdateStateOfOrder(ourOrder)));
                        validInput = true;
                    } catch (GivenStateIsInvalidException e) {
                        validInput = false;
                        view.displayErrorMessage(e.getMessage());

                    }
                }
//Ask to change PRODUCT
                validInput = false;
                while (!validInput) {
                    try {
                        view.displayMessage("Current Material: " + ourOrder.getProduct().getMaterial());
                        view.displayAvailableMaterialFromProductFile(service.retrieveAllProductInfo());
                        ourOrder.setProduct(service.obtainProductInfoByMaterial(view.getUpdateMaterialOfOrder(ourOrder)));
                        validInput = true;
                    } catch (GivenProductMaterialIsInvalidException e) {
                        validInput = false;
                        view.displayErrorMessage(e.getMessage());
                    }
                }
//Ask to change AREA
                view.displayMessage(Double.toString(ourOrder.getArea()));
                view.getUpdatedAreaForOrder(ourOrder);
                if (originalOrder.getOrderDate().equals(ourOrder.getOrderDate())) {
                    service.updateOrder(ourOrder);
                } else {
                    service.updateOrder(ourOrder);
                    service.removeOrder(originalOrder);
                }
                view.displayOrderInfo(ourOrder);
                view.bannerSuccessfulUpdateOfOrder();
                view.askUserToPressEnterToContinue();
        } else {
                view.bannerOrderRemovalRequestWithdrawn();
                view.askUserToPressEnterToContinue();
            }
        } catch (FlooringOrderPersistenceException | FlooringOrderNumberDoesNotExistException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
}
