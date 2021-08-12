package com.opendev.odtask.ui;

import com.opendev.odtask.connection.ConnectionProvider;
import com.opendev.odtask.core.dao.EquipmentDao;
import com.opendev.odtask.core.dao.WellDao;
import com.opendev.odtask.core.dao.impl.EquipmentDaoImpl;
import com.opendev.odtask.core.dao.impl.WellDaoImpl;
import com.opendev.odtask.core.domain.Equipment;
import com.opendev.odtask.core.domain.Well;
import com.opendev.odtask.formatter.Formatter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UserInterface {

    private final static String FILE_NAME = "dbData.xml";

    private final EquipmentDao equipmentDao;

    private final WellDao wellDao;

    private final Scanner scanner;

    public UserInterface(ConnectionProvider connectionProvider) {
        scanner = new Scanner(System.in);
        equipmentDao = new EquipmentDaoImpl(connectionProvider);
        wellDao = new WellDaoImpl(connectionProvider, equipmentDao);
    }

    public void run() {
        printMainMenu();
        switch (scanner.next()) {
            case "a":
                countEquipmentForWellsWithNames();
                break;
            case "b":
                addEquipmentToWell();
                break;
            case "c":
                showAvailableWells();
                break;
            case "d":
                downloadDataInXml();
                break;
            case "q":
                break;
            default:
                System.out.println("Only 'a'-'f' and 'q' available");
        }
        scanner.close();
    }

    private void printMainMenu() {
        System.out.println("Menu:");
        System.out.println("a. Count equipment for wells with names");
        System.out.println("b. Create equipment for well. ");
        System.out.println("c. Show all available wells");
        System.out.println("d. Download data in xml file");
        System.out.println("Press 'a'-'f' or 'q' for exit");
    }

    private void countEquipmentForWellsWithNames() {
        System.out.println("Enter well names separated by commas or spaces");
        scanner.nextLine();
        String inputString = scanner.nextLine();
        String[] names = inputString.split("\\s|,");
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : names) {
            if (checkIfWellByNameExists(name)) {
                long count = equipmentDao.countByWellName(name.trim());
                stringBuilder.append(name).append(" - ").append(count).append(System.lineSeparator());
            } else {
                stringBuilder.append(name).append(" doesn't exist.").append(System.lineSeparator());
            }
        }
        System.out.println("Current equipments count:");
        System.out.println(stringBuilder.toString());
        returnToMainMenuSelector();
    }

    private void addEquipmentToWell() {
        System.out.println("Enter well name to add equipment");
        String inputName = scanner.next();
        long wellId = wellDao.findWellIdByName(inputName);
        if (wellId != 0) {
            getQuantityOfEquipmentAndAssignToWell(wellId);
        } else {
            System.out.println("Unable to find well with name " + inputName);
            System.out.println("Creating new well with name " + inputName);
            wellDao.save(new Well(inputName));
            long savedWellId = wellDao.findWellIdByName(inputName);
            getQuantityOfEquipmentAndAssignToWell(savedWellId);
        }
        System.out.println("Equipment successfully added.");
        returnToMainMenuSelector();
    }

    private void downloadDataInXml() {
        List<Well> data = wellDao.findAll();
        String dbData = Formatter.formatDataToXml(data);
        try (FileWriter fileWriter = new FileWriter(FILE_NAME);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(dbData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        returnToMainMenuSelector();
    }

    private void showAvailableWells() {
        List<Well> result = wellDao.findAll();
        result.forEach(System.out::println);
        returnToMainMenuSelector();
    }

    private void returnToMainMenuSelector() {
        System.out.println("Press Y to return for main menu or any for exit");
        String answer = scanner.next();
        if (answer.equals("y")) {
            run();
        } else {
            scanner.close();
        }
    }

    private void getQuantityOfEquipmentAndAssignToWell(Long wellId) {
        System.out.println("Enter quantity of equipment to add");
        String inputQuantity = scanner.next();
        int quantity = parseInputStringToInt(inputQuantity);
        if (quantity == 0) {
            System.out.println("Equipment quantity should be number and > 0");
            run();
        }
        for (int i = 0; i < quantity; i++) {
            equipmentDao.save(new Equipment(UUID.randomUUID().toString(), wellId));
        }
    }

    private boolean checkIfWellByNameExists(String name) {
        long id = wellDao.findWellIdByName(name);
        return id != 0;
    }

    private int parseInputStringToInt(String inputString) {
        try {
            return Integer.parseInt(inputString);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
