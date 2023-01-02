package org.example;

import med.model.Appointment;
import med.model.Location;
import med.model.Person;
import med.model.Treatment;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
         executorService.submit(new ClientRequest("127.0.0.1", 55555, getRequest()));
    }

    public static RequestData getRequest() {
        return new AppointmentRequestData(new Appointment(new Person("Popescu Ion", "0121234124"),
                new Treatment(13.50, 13, 30), new Location("Regina Maria"), LocalDateTime.now()));
    }
}