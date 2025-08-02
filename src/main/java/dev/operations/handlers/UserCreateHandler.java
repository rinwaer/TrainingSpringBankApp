package dev.operations.handlers;

import dev.model.User;
import dev.operations.OperationHandler;
import dev.services.UserService;

import java.util.Scanner;

public class UserCreateHandler implements OperationHandler {

    private final Scanner sc;

    private final UserService userService;

    public UserCreateHandler(Scanner sc, UserService userService) {
        this.sc = sc;
        this.userService = userService;
    }

    @Override
    public void processing() {
        System.out.println("Enter login for new user");
        String login = sc.nextLine();
        User user = userService.createUser(login);
        System.out.println("User created: " + user.toString());

    }
}
