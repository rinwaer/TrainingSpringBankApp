package dev.operations.handlers;

import dev.user.User;
import dev.operations.OperationHandler;
import dev.operations.OperationsTypes;
import dev.account.AccountService;
import dev.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountCreateHandler implements OperationHandler {

    private final Scanner sc;

    private final UserService userService;

    private final AccountService accountService;

    public AccountCreateHandler(Scanner sc, UserService userService, AccountService accountService) {
        this.sc = sc;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void processing() {
        System.out.println("Enter id of user to create an account");
        int userId = sc.nextInt();
        User user = userService.findUser(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " not found"));
        var account = accountService.createAccount(user);
        user.getAccounts().add(account);
        System.out.printf("Account created with Id: %s for user: %s%n", account.getId(), user.getLogin());
    }

    @Override
    public OperationsTypes getOperationType() {
        return OperationsTypes.ACCOUNT_CREATE;
    }
}
