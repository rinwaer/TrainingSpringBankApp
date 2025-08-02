package dev.operations;


import dev.operations.handlers.*;
import dev.services.AccountService;
import dev.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class OperationsHandlersConfiguration {

    @Bean
    public UserCreateHandler userCreateHandler(Scanner sc,
                                               UserService userService) {
        return new UserCreateHandler(sc, userService);
    }

    @Bean
    public ShowAllUsersHandler showAllUsersHandler(UserService userService) {
        return new ShowAllUsersHandler(userService);
    }

    @Bean
    public AccountWithdrawHandler accountWithdrawHandler(Scanner sc,
                                                         AccountService accountService) {
        return new AccountWithdrawHandler(sc,accountService);
    }

    @Bean
    public AccountTransferHandler accountTransferHandler(Scanner sc,
                                                         AccountService accountService) {
        return new AccountTransferHandler(sc, accountService);
    }

    @Bean
    public AccountDepositHandler accountDepositHandler(Scanner sc,
                                                       AccountService accountService) {
        return new AccountDepositHandler(sc, accountService);
    }


    @Bean
    AccountCreateHandler accountCreateHandler(Scanner sc,
                                              UserService userService,
                                              AccountService accountService) {
        return new AccountCreateHandler(sc, userService, accountService);
    }

    @Bean
    AccountCloseHandler accountCloseHandler(Scanner sc,
                                            AccountService accountService,
                                            UserService userService) {
        return new AccountCloseHandler(sc, accountService, userService);
    }

}
