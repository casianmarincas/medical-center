package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        while (true) {
            executorService.submit(new ClientRequest("127.0.0.1", 55555, getRequest()));
        }
    }

    public static Object getRequest() {
        return null;
    }
}