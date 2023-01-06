package org.example;

import med.model.*;
import med.networking.RequestType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class Randomizer {

    public static RequestData getRandomAppointmentRequestData(List<Person> personList, List<Treatment> treatmentList, List<Location> locationList) {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(personList.size());
        Person person = personList.get(rand_int1);
        int rand_int2 = rand.nextInt(treatmentList.size());
        Treatment treatment = treatmentList.get(rand_int2);
        int rand_int3 = rand.nextInt(locationList.size());
        Location location = locationList.get(rand_int3);

        Appointment appointment = new Appointment(person, treatment, location, LocalDateTime.now());
        return new RequestData(RequestType.ADD_APPOINTMENT, appointment);
    }

}
