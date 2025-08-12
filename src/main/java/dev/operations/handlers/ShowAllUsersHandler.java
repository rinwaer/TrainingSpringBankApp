package dev.operations.handlers;

import dev.operations.OperationHandler;
import dev.operations.OperationsTypes;
import dev.user.UserService;
import org.springframework.stereotype.Component;

@Component
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

    @Override
    public OperationsTypes getOperationType() {
        return OperationsTypes.SHOW_ALL_USERS;
    }
}
