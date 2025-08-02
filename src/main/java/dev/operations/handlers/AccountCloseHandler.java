package dev.operations.handlers;

import dev.model.Account;
import dev.model.User;
import dev.operations.OperationHandler;
import dev.services.AccountService;
import dev.services.UserService;

import java.util.Scanner;

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
}
