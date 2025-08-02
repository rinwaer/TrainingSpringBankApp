package dev.services;

import dev.operations.OperationHandler;
import dev.operations.OperationsTypes;

import java.sql.SQLOutput;
import java.util.Map;
import java.util.Scanner;

public class OperationsConsoleListener {

    private final Scanner sc;

    private final Map<OperationsTypes, OperationHandler> operationsMap;

    public OperationsConsoleListener(Scanner sc, Map<OperationsTypes, OperationHandler> operationsMap) {
        this.sc = sc;
        this.operationsMap = operationsMap;
    }

    public void listenInput() {
        while(true) {
            var operationType = nextOperation();
            handleNextOperation(operationType);
        }

    }

    private OperationsTypes nextOperation() {
        System.out.println("Choose operation:");
        listOfOperations();
        while(true) {
            var nextOperation = sc.nextLine();
            try {
                return OperationsTypes.valueOf(nextOperation);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid operation");
            }
        }
    }

    private void handleNextOperation(OperationsTypes operation) {
        try {
            var proccess = operationsMap.get(operation);
            proccess.processing();
        } catch (Exception e) {
            System.out.printf(
                    "Error command %s: error = %s%n", operation, e.getMessage());
        }
    }

    private void listOfOperations() {
        System.out.println("USER_CREATE");
        System.out.println("SHOW_ALL_USERS");
        System.out.println("ACCOUNT_CREATE");
        System.out.println("ACCOUNT_DEPOSIT");
        System.out.println("ACCOUNT_WITHDRAW");
        System.out.println("ACCOUNT_TRANSFER");
        System.out.println("ACCOUNT_CLOSE");
    }
}