/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author jswan
 */
public class GlobalIdDaoTrainingFileImpl implements GlobalIdDao {

    private int nextNumber;
    Set<Integer> globalIDs = new HashSet<>();
    final String ID_FILE = "global_id_stub_file.txt";

    public GlobalIdDaoTrainingFileImpl() throws GlobalIdInfoPersistenceException {
        loadGlobalIdFile();
    }

    @Override
    public int getNewID() throws GlobalIdInfoPersistenceException {
        if (nextNumber == 0) {
            loadGlobalIdFile();
            nextNumber = maxId();
        }
        nextNumber++;
        globalIDs.add(nextNumber);
        writeGlobalID();

        return nextNumber;
    }

    private int maxId() {
        int number = 0;
        Set<Integer> entity = globalIDs;
        for (Integer k : entity) {
            if (k > number) {
                number = k;
            }
        }

        return number;
    }

    private void loadGlobalIdFile() throws GlobalIdInfoPersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ID_FILE)));

        } catch (FileNotFoundException e) {
            throw new GlobalIdInfoPersistenceException(
                    "-_- Could not load " + ID_FILE + " Id File into memory.", e);
        }

        int currentLine;
        while (scanner.hasNextLine()) {
            currentLine = Integer.parseInt(scanner.nextLine());
            globalIDs.add(currentLine);
        }
        scanner.close();
    }

    private void writeGlobalID() throws GlobalIdInfoPersistenceException {
        //do nothing
    }
}
