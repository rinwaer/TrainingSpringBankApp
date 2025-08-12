package dev;


import dev.operations.OperationHandler;
import dev.operations.OperationsTypes;
import dev.account.AccountService;
import dev.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


@Configuration
@ComponentScan("dev")
@PropertySource("classpath:application.properties")
public class SpringConfig {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}
