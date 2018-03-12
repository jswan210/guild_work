/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author jswan
 */
public class FlooringTaxDaoFileImpl implements FlooringTaxDao {

    private Map<String, Tax> taxes = new HashMap<>();
    final String DELIMITER = ",";
    final String TAX_FILE = "Taxes.txt";
    final static int STATE = 0;
    final static int TAX_RATE = 1;

    @Override
    public List<Tax> retrieveAllTaxRates() throws TaxInfoPersistenceException {
        loadTaxFile();
        return new ArrayList<>(taxes.values());
    }

    @Override
    public Tax obtainTaxRateByState(String state) throws TaxInfoPersistenceException {

        loadTaxFile();
        List<Tax> taxRateList = taxes.values()
                .stream()
                .filter(o -> o.getState().equalsIgnoreCase(state))
                .collect(Collectors.toList());

        Tax tax = taxRateList.get(0);

        return tax;

    }

    public void loadTaxFile() throws TaxInfoPersistenceException {
        Scanner scanner;

        try {

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TAX_FILE)));

        } catch (FileNotFoundException e) {
            throw new TaxInfoPersistenceException(
                    "-_- Could not load Tax Info into memory.", e);
        }

        String currentLine;
        String[] currentTokens;
        scanner.nextLine();
        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);

            Tax currentTax = new Tax();

            currentTax.setState(currentTokens[STATE]);
            currentTax.setTaxRate(Double.parseDouble(currentTokens[TAX_RATE]));

            taxes.put(currentTax.getState(), currentTax);
        }

        scanner.close();
    }

}
