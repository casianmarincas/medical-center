package org.example;

import med.model.*;
import med.networking.RequestType;
import med.networking.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        GetAllRequest request = new GetAllRequest("127.0.0.1", 55555);

        List<Callable<String>> taskList = new ArrayList<>();
        taskList.add(request);

        executorService.invokeAll(taskList);

        List<Person> personList = request.getPersonList();
        List<Treatment> treatmentList = request.getTreatmentList();
        List<Location> locationList = request.getLocationList();

        System.out.println(locationList);
//        System.out.println(locationList.size());
//        System.out.println(treatmentList.size());
//        System.out.println(personList.size());

        Thread[] creators = new Thread[10];
        int i = 0;
        for (Person p: personList) {
            creators[i] = new RequestsCreator(p, executorService, treatmentList, locationList);
            creators[i].start();
            i++;
        }

        for (i = 0; i < 10; i++) {
            creators[i].join();
        }

        executorService.shutdown();
    }
}