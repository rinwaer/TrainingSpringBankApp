package dev;

import dev.model.User;
import dev.services.AccountService;
import dev.services.UserService;

import java.util.Scanner;

public class OperationsConsoleListener {

    private final Scanner sc;

    private final UserService userService;
    private final AccountService accountService;

    public OperationsConsoleListener(Scanner sc, UserService userService, AccountService accountService) {
        this.sc = sc;
        this.userService = userService;
        this.accountService = accountService;
    }

    public void listenInput() {
        while(true) {
            System.out.println("Choose operation:");
            var nextOperation = sc.nextLine();
            try {
                switch (nextOperation) {
                    case "USER_CREATE":
                        System.out.println("Enter login for new user");
                        String login = sc.nextLine();
                        User user = userService.createUser(login);
                        System.out.println("User created: " + user.toString());
                        break;
                    case "SHOW_ALL_USERS":
                        System.out.println("List of all users: ");
                        userService.getAllUsers().forEach(System.out::println);
                        break;
                    case "ACCOUNT_CREATE":
                        System.out.println("Enter id of user to create an account");
                        int userId = sc.nextInt();
                        user = userService.findUser(userId)
                                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " not found"));
                        var account = accountService.createAccount(user);
                        user.getAccounts().add(account);
                        System.out.println("Account created with Id: %s for user: %s".formatted(account.getId(), user.getLogin()));
                        break;
                    case "ACCOUNT_DEPOSIT":
                        System.out.println("Enter id of account to deposit");
                        int id = sc.nextInt();
                        System.out.println("Enter amount of money to deposit");
                        int amount = sc.nextInt();
                        accountService.deposit(id, amount);
                        break;
                    case "ACCOUNT_WITHDRAW":
                        System.out.println("Enter id of account to withdraw");
                        id = sc.nextInt();
                        System.out.println("Enter amount of money to withdraw");
                        amount = sc.nextInt();
                        accountService.withdraw(id, amount);
                    case "ACCOUNT_CLOSE":
                        System.out.println("Enter id of account to close");
                        id = sc.nextInt();
                        account = accountService.close(id);
                        user = userService.findUser(account.getUserId())
                                .orElseThrow(() -> new IllegalStateException("User with id " + account.getUserId() + " not found"));
                        user.getAccounts().remove(account);
                        break;
                    case "ACCOUNT_TRANSFER":
                        System.out.println("Enter id of sender`s account");
                        int senderId = sc.nextInt();
                        System.out.println("Enter id of receiver`s account");
                        int receiverId = sc.nextInt();
                        System.out.println("Enter amount of money to transfer");
                        amount = sc.nextInt();
                        accountService.transfer(senderId, receiverId, amount);
                        System.out.println("Transfer successful");
                        break;

                }
            } catch (Exception e) {
                System.out.printf(
                        "Error command %s: error = %s%n", nextOperation, e.getMessage()
                );
            }

        }

    }
}
//убейте меня за эти 100 строчек кода в одном файле