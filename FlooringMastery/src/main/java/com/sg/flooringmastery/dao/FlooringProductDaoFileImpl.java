/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
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
public class FlooringProductDaoFileImpl implements FlooringProductDao {

    private Map<String, Product> products = new HashMap<>();
    final String DELIMITER = ",";
    final String PRODUCT_FILE = "Products.txt";
    final static int MATERIAL = 0;
    final static int MATERIAL_COST_PER_SQ_FT = 1;
    final static int LABOR_COST_PER_SQ_FT = 2;
    

    @Override
    public Product obtainProductInfoByMaterial(String material) throws ProductInfoPersistenceException {
        loadProductInfo();
        List<Product> productList = products.values()
                .stream()
                .filter(p -> p.getMaterial().equalsIgnoreCase(material))
                .collect(Collectors.toList());
        Product product = productList.get(0);
        return product;
    }

    @Override
    public List<Product> retrieveAllProductInfo() throws ProductInfoPersistenceException {
        loadProductInfo();
        return new ArrayList<>(products.values());
    }

    public void loadProductInfo() throws ProductInfoPersistenceException {
        Scanner scanner;

        try {

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCT_FILE)));

        } catch (FileNotFoundException e) {
            throw new ProductInfoPersistenceException(
                    "-_- Could not load Product Information into memory.", e);
        }

        String currentLine;
        String[] currentTokens;
        scanner.nextLine();

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);

            Product currentProduct = new Product();
            currentProduct.setMaterial(currentTokens[MATERIAL]);
            currentProduct.setMaterialCostPerSqFt(new BigDecimal(currentTokens[MATERIAL_COST_PER_SQ_FT]));
            currentProduct.setLaborCostPerSqFt(new BigDecimal(currentTokens[LABOR_COST_PER_SQ_FT]));

            products.put(currentProduct.getMaterial(), currentProduct);
        }

        scanner.close();
    }
}
