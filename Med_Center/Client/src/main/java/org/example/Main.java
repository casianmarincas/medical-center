package org.example;

import med.model.Location;
import med.model.Person;
import med.model.Treatment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        GetAllRequest request = new GetAllRequest("127.0.0.1", 55555);

        List<Callable<String>> taskList = new ArrayList<>();
        taskList.add(request);

        executorService.invokeAll(taskList);

        List<Person> personList = request.getPersonList();
        List<Treatment> treatmentList = request.getTreatmentList();
        List<Location> locationList = request.getLocationList();

//        System.out.println(locationList.size());
//        System.out.println(treatmentList.size());
//        System.out.println(personList.size());

        List<Callable<Object>> tasks = Randomizer.getRequests(personList, treatmentList, locationList);
        executorService.invokeAll(tasks);
    }

}