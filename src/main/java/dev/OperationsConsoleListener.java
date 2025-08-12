package dev;

import dev.operations.OperationHandler;
import dev.operations.OperationsTypes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class OperationsConsoleListener {

    private final Scanner sc;

    private final Map<OperationsTypes, OperationHandler> operationsMap;

    public OperationsConsoleListener(Scanner sc,
                                     List<OperationHandler> handlerList) {
        this.sc = sc;
        this.operationsMap = handlerList
                .stream()
                .collect(
                        Collectors.toMap(
                                OperationHandler::getOperationType,
                                handler -> handler
                        )
                );
    }

    public void listenInput() {
        while(!Thread.currentThread().isInterrupted()) {
            var operationType = nextOperation();
            if (operationType == null) {
                return;
            }
            handleNextOperation(operationType);
        }

    }

    private void printAllAvailableOperations() {
        operationsMap.keySet().forEach(System.out::println);
    }

    private OperationsTypes nextOperation() {
        System.out.println("Choose operation:");
        printAllAvailableOperations();
        System.out.println();
        while(!Thread.currentThread().isInterrupted()) {
            var nextOperation = sc.nextLine();
            try {
                return OperationsTypes.valueOf(nextOperation);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid operation");
            }
        }
        return null;
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
}