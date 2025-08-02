package dev.operations.handlers;

import dev.operations.OperationHandler;
import dev.services.UserService;

public class ShowAllUsersHandler implements OperationHandler {

    UserService userService;

    public ShowAllUsersHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processing() {
        System.out.println("List of all users: ");
        userService.getAllUsers().forEach(System.out::println);
    }
}
