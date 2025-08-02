package dev.operations.handlers;

import dev.operations.OperationHandler;
import dev.services.AccountService;

import java.util.Scanner;

public class AccountWithdrawHandler implements OperationHandler {

    private final Scanner sc;

    private final AccountService accountService;

    public AccountWithdrawHandler(Scanner sc, AccountService accountService) {
        this.sc = sc;
        this.accountService = accountService;
    }

    @Override
    public void processing() {
        System.out.println("Enter id of account to withdraw");
        int id = sc.nextInt();
        System.out.println("Enter amount of money to withdraw");
        int amount = sc.nextInt();
        accountService.withdraw(id, amount);
    }
}
