package org.example;

import med.model.Appointment;
import med.model.Location;
import med.model.Person;
import med.model.Treatment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {


         GetAllRequest request = new GetAllRequest("127.0.0.1", 55555);

        List<Callable<String>> taskList = new ArrayList<>();
        taskList.add(request);

        executorService.invokeAll(taskList);

         List<Person> personList = request.getPersonList();
         List<Treatment> treatmentList = request.getTreatmentList();
         List<Location> locationList = request.getLocationList();

         System.out.println(locationList.size());
        System.out.println(treatmentList.size());
        System.out.println(personList.size());
         for(int i=0; i<2;i++){
             RequestData requestData = getRandomAppointmentRequestData(personList, treatmentList, locationList);
             executorService.submit(new ClientRequest("127.0.0.1", 55555, requestData));

         }

    }

    public static RequestData getRandomAppointmentRequestData(List<Person> personList, List<Treatment> treatmentList, List<Location> locationList){
        Random rand = new Random();
        int rand_int1 = rand.nextInt(personList.size());
        Person person = personList.get(rand_int1);
        int rand_int2 = rand.nextInt(treatmentList.size());
        Treatment treatment = treatmentList.get(rand_int2);
        int rand_int3 = rand.nextInt(locationList.size());
        Location location = locationList.get(rand_int3);

        Appointment appointment = new Appointment(person, treatment, location, LocalDateTime.now());
        return new AppointmentRequestData(appointment);

    }


}