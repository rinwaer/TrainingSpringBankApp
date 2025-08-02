package dev.operations.handlers;

import dev.operations.OperationHandler;
import dev.services.AccountService;

import java.util.Scanner;

public class AccountDepositHandler implements OperationHandler {

    private final Scanner sc;

    private final AccountService accountService;

    public AccountDepositHandler(Scanner sc, AccountService accountService) {
        this.sc = sc;
        this.accountService = accountService;
    }

    @Override
    public void processing() {
        System.out.println("Enter id of account to deposit");
        int id = sc.nextInt();
        System.out.println("Enter amount of money to deposit");
        int amount = sc.nextInt();
        accountService.deposit(id, amount);
    }
}
