package com.statemachine.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class Utility implements Runnable {

    private enum States {
        RED, GREEN, YELLOW
    }

    private volatile boolean running = true;
    private States currentState = States.RED;
    private final long stateDuration = 10000;
    private final long maxRunTime = 60000;

    @PostConstruct
    public void init() {
        Thread stateMachineThread = new Thread(this);
        stateMachineThread.start();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        while (running) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime >= maxRunTime) {
                running = false;
                System.out.println("Encerrando a máquina de estados...");
                break;
            }

            switch (currentState) {
                case RED:
                    System.out.println("Estado: RED ⌊ ● ○ ○ ⌉");
                    sleep(stateDuration);
                    currentState = States.GREEN;
                    break;
                case GREEN:
                    System.out.println("Estado: GREEN ⌊ ○ ○ ● ⌉");
                    sleep(stateDuration);
                    currentState = States.YELLOW;
                    break;
                case YELLOW:
                    System.out.println("Estado: YELLOW ⌊ ○ ● ○ ⌉");
                    sleep(stateDuration);
                    currentState = States.RED;
                    break;
            }
        }
    }

    private void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrompida.");
        }
    }
}