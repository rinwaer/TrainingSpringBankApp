package dev.config;


import dev.operations.OperationHandler;
import dev.operations.OperationsTypes;
import dev.operations.handlers.*;
import dev.services.AccountService;
import dev.services.OperationsConsoleListener;
import dev.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;
import java.util.Scanner;


@Configuration
@ComponentScan("dev")
@PropertySource("classpath:application.properties")
public class SpringConfig {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public OperationsConsoleListener operationsConsoleListener(
            Scanner scanner,
            UserCreateHandler userCreateHandler,
            ShowAllUsersHandler showAllUsersHandler,
            AccountWithdrawHandler accountWithdrawHandler,
            AccountTransferHandler accountTransferHandler,
            AccountDepositHandler accountDepositHandler,
            AccountCreateHandler accountCreateHandler,
            AccountCloseHandler accountCloseHandler
            ) {
        Map<OperationsTypes, OperationHandler> map =
                Map.of(
                        OperationsTypes.USER_CREATE, userCreateHandler,
                        OperationsTypes.SHOW_ALL_USERS, showAllUsersHandler,
                        OperationsTypes.ACCOUNT_WITHDRAW, accountWithdrawHandler,
                        OperationsTypes.ACCOUNT_TRANSFER, accountTransferHandler,
                        OperationsTypes.ACCOUNT_DEPOSIT, accountDepositHandler,
                        OperationsTypes.ACCOUNT_CREATE, accountCreateHandler,
                        OperationsTypes.ACCOUNT_CLOSE, accountCloseHandler
                );
        return new OperationsConsoleListener(scanner, map);
    }

    @Bean
    public UserService userService(AccountService accountService) {
        return new UserService(accountService);
    }

    @Bean
    public AccountService accountService(
            @Value("${account.default-amount}") int defaultAmount,
            @Value("${account.transfer-commision}") double transferComission
    ) {
        return new AccountService(defaultAmount, transferComission);
    }
}
