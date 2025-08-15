package dev.operations.handlers;

import dev.account.Account;
import dev.user.User;
import dev.operations.OperationHandler;
import dev.operations.OperationsTypes;
import dev.account.AccountService;
import dev.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountCloseHandler implements OperationHandler {

    private final Scanner sc;

    private final AccountService accountService;

    private final UserService userService;

    public AccountCloseHandler(Scanner sc, AccountService accountService, UserService userService) {
        this.sc = sc;
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void processing() {
        System.out.println("Enter id of account to close");
        var id = Long.parseLong(sc.nextLine());
        accountService.close(id);

        System.out.println("Account %id successfully closed".formatted(id));
    }

    @Override
    public OperationsTypes getOperationType() {
        return OperationsTypes.ACCOUNT_CLOSE;
    }
}
