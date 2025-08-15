package dev.operations.handlers;

import dev.operations.OperationHandler;
import dev.operations.OperationsTypes;
import dev.account.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
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
        var id = Long.parseLong(sc.nextLine());
        System.out.println("Enter amount of money to deposit");
        int amount = sc.nextInt();
        accountService.deposit(id, amount);
    }

    @Override
    public OperationsTypes getOperationType() {
        return OperationsTypes.ACCOUNT_DEPOSIT;
    }
}
