package dev.operations.handlers;

import dev.operations.OperationHandler;
import dev.operations.OperationsTypes;
import dev.account.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
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
        var senderId = Long.parseLong(sc.nextLine());
        System.out.println("Enter id of receiver`s account");
        var receiverId = Long.parseLong(sc.nextLine());
        System.out.println("Enter amount of money to transfer");
        int amount = Integer.parseInt(sc.nextLine());
        accountService.transfer(senderId, receiverId, amount);
        System.out.println("Transfer successful");
    }

    @Override
    public OperationsTypes getOperationType() {
        return OperationsTypes.ACCOUNT_TRANSFER;
    }

}
