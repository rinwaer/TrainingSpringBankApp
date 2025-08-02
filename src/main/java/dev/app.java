package dev;

import dev.services.OperationsConsoleListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class app {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("dev");
        context.getBean(OperationsConsoleListener.class)
                .listenInput();
    }
}
