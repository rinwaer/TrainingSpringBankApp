package dev.config;


import dev.services.AccountService;
import dev.OperationsConsoleListener;
import dev.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
    public OperationsConsoleListener operationsConsoleListener(Scanner scanner,
                                                               UserService userService,
                                                               AccountService accountService) {
        return new OperationsConsoleListener(scanner, userService, accountService);
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
