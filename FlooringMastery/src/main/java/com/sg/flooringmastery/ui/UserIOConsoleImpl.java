/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author jswan
 */
public class UserIOConsoleImpl implements UserIO {

    static Scanner usersInput = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readPostiveDouble(String prompt) {
        boolean continueToCheckNumber = true;
        double doubleNumber = -1;
        while (continueToCheckNumber) {
            System.out.println(prompt);
            String theChoice = usersInput.nextLine();

            try {
                doubleNumber = Double.parseDouble(theChoice);
                if (doubleNumber <= -1) {
                    System.out.println("Need a positive integer.");
                    continueToCheckNumber = true;

                } else {
                    return doubleNumber;
                }
            } catch (NumberFormatException e) {
                System.out.println("Need an integer");
            }

        }
        return doubleNumber;
    }

    @Override
    public double readDouble(String prompt) {
        boolean continueToCheckNumber = true;
        double doubleNumber = 0;
        do {
            System.out.println(prompt);
            String theChoice = usersInput.nextLine();

            try {

                doubleNumber = Double.parseDouble(theChoice);
                continueToCheckNumber = false;

            } catch (NumberFormatException e) {
                System.out.println("Need an integer");
            }

        } while (continueToCheckNumber);
        return doubleNumber;
    }

    @Override
    public double readDoubleIfEmptyReturnsZero(String prompt) {
        boolean continueToCheckNumber = true;
        double doubleNumber = -1;
        do {
            System.out.println(prompt);
            String theChoice = usersInput.nextLine();

            try {
                if (theChoice.isEmpty()) {
                    return doubleNumber;
                }
                doubleNumber = Double.parseDouble(theChoice.toString());
                continueToCheckNumber = false;

            } catch (NumberFormatException e) {
                System.out.println("Need an integer");
            }
        } while (continueToCheckNumber);
        return doubleNumber;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {

        boolean continueToDoStuff = true;
        double chosen = 0;
        do {
            System.out.println(prompt);

            String theChoice = usersInput.nextLine();
            try {
                chosen = Double.parseDouble(theChoice);

                if (chosen >= min && chosen <= max) {
                    continueToDoStuff = false;
                } else {
                    System.out.println("Incorrect value, enter another value:");
                }
            } catch (NumberFormatException e) {
                System.out.println("Try another number");
            }
        } while (continueToDoStuff);
        return chosen;
    }

    @Override
    public float readFloat(String prompt) {

        boolean continueToCheckNumber = true;
        float number = 0;
        System.out.println(prompt);

        String theChoice = usersInput.nextLine();
        do {
            try {
                number = Float.parseFloat(theChoice);
                continueToCheckNumber = false;

            } catch (NumberFormatException e) {
                System.out.println("Need an integer");
            }
        } while (continueToCheckNumber);
        return number;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        boolean continueToDoStuff = true;
        float chosen = 0;
        do {
            System.out.println(prompt);

            String theChoice = usersInput.nextLine();
            try {
                chosen = Float.parseFloat(theChoice);

                if (chosen >= min && chosen <= max) {
                    continueToDoStuff = false;
                } else {
                    System.out.println("Incorrect value, enter another value:");
                }
            } catch (NumberFormatException e) {
                System.out.println("Try another number");
            }

        } while (continueToDoStuff);
        return chosen;
    }

    @Override
    public int readInt(String prompt) {
        boolean continueToCheckNumber = true;
        int number = 0;
        do {
            System.out.println(prompt);
            String theChoice = usersInput.nextLine();

            try {
                number = Integer.parseInt(theChoice);
                continueToCheckNumber = false;
            } catch (NumberFormatException e) {
                System.out.println("Need an integer");
            }
        } while (continueToCheckNumber);
        return number;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        boolean continueToCheckNumber = true;
        int chosen = 0;
        do {
            System.out.println(prompt);

            String theChoice = usersInput.nextLine();
            try {
                chosen = Integer.parseInt(theChoice);

                if (chosen >= min && chosen <= max) {
                    continueToCheckNumber = false;
                } else {
                    System.out.println("Incorrect value, enter another value between "
                            + min + " & " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Try another number");
            }
        } while (continueToCheckNumber);

        return chosen;
    }

    @Override
    public long readLong(String prompt) {
        boolean continueToCheckNumber = true;
        long longNumber = 0;
        System.out.println(prompt);

        String theChoice = usersInput.nextLine();
        do {
            try {
                longNumber = Long.parseLong(theChoice);
                continueToCheckNumber = false;

            } catch (NumberFormatException e) {
                System.out.println("Need an integer");
            }
        } while (continueToCheckNumber);
        return longNumber;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        boolean continueToDoStuff = true;
        long chosen = 0;
        do {
            System.out.println(prompt);

            String theChoice = usersInput.nextLine();
            try {
                chosen = Long.parseLong(theChoice);

                if (chosen >= min && chosen <= max) {
                    continueToDoStuff = false;
                } else {
                    System.out.println("Incorrect value, enter another value:");
                }
            } catch (NumberFormatException e) {
                System.out.println("Try another number");
            }
        } while (continueToDoStuff);
        return chosen;
    }

    @Override
    public String readString(String prompt) {
        String ourString;
        System.out.println(prompt);
        ourString = usersInput.nextLine();

        return ourString;
    }

    @Override
    public boolean readBoolean(String prompt, String answerIfTrue, String answerIfFalse) {
        boolean continueToDoStuff = true;
        boolean chosen = false;
        do {
            System.out.println(prompt);
            String theChoice = usersInput.nextLine();

            if (theChoice.equalsIgnoreCase(answerIfTrue)) {
                chosen = true;
                continueToDoStuff = false;
            } else if (theChoice.equalsIgnoreCase(answerIfFalse)) {
                chosen = false;
                continueToDoStuff = false;
            } else {
                System.out.println("Please us the correct responce.");

            }
        } while (continueToDoStuff);
        return chosen;
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        boolean continueToCheckNumber = true;
        LocalDate date = null;
        do {
            try {
                System.out.println(prompt);
                String theChoice = usersInput.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
                date = LocalDate.parse(theChoice, formatter);
                continueToCheckNumber = false;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Date Format. \nPlease use this format MM/DD/YYYY");
                continueToCheckNumber = true;
            }
        } while (continueToCheckNumber);
        return date;
    }

    @Override
    public LocalDate readLocalDateAllowsNoInputFromUser(String prompt) {
        boolean continueToCheckNumber = true;
        LocalDate date = null;
        do {
            try {
                System.out.println(prompt);
                String theChoice = usersInput.nextLine();
                if (theChoice.isEmpty()) {
                    return date;
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
                    date = LocalDate.parse(theChoice, formatter);
                    continueToCheckNumber = false;
                }

            } catch (DateTimeParseException e) {
                System.out.println("Invalid Date Format. \nPlease use this format MMDDYYYY");
                continueToCheckNumber = true;
            }
        } while (continueToCheckNumber);
       return date; 
    }
}
