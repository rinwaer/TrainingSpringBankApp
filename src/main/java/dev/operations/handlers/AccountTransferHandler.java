package dev.operations.handlers;

import dev.operations.OperationHandler;
import dev.services.AccountService;

import java.util.Scanner;

public class AccountTransferHandler implements OperationHandler {

    private final Scanner sc;
    private final AccountService accountService;

    public AccountTransferHandler(Scanner sc, AccountService accountService) {
        this.sc = sc;
        this.accountService = accountService;
    }

    @Override
    public void processing() {
        System.out.println("Enter id of sender`s account");
        int senderId = sc.nextInt();
        System.out.println("Enter id of receiver`s account");
        int receiverId = sc.nextInt();
        System.out.println("Enter amount of money to transfer");
        int amount = sc.nextInt();
        accountService.transfer(senderId, receiverId, amount);
        System.out.println("Transfer successful");
    }
}
