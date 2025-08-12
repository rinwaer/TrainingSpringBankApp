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
        int id = sc.nextInt();
        Account account = accountService.close(id);
        User user = userService.findUser(account.getUserId())
                .orElseThrow(() -> new IllegalStateException("User with id " + account.getUserId() + " not found"));
        user.getAccounts().remove(account);
    }

    @Override
    public OperationsTypes getOperationType() {
        return OperationsTypes.ACCOUNT_CLOSE;
    }
}
