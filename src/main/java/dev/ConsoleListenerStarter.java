package dev;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class ConsoleListenerStarter {

    private final OperationsConsoleListener consoleListener;

    private Thread consoleListenerThread;

    public ConsoleListenerStarter(OperationsConsoleListener operationsConsoleListener) {
        this.consoleListener = operationsConsoleListener;
    }

    @PostConstruct
    public void postConstruct() {
        this.consoleListenerThread = new Thread(() -> {
            consoleListener.listenInput();
        });
        consoleListenerThread.start();
    }

    @PreDestroy
    public void preDestroy() {
        this.consoleListenerThread.interrupt();
    }
}
