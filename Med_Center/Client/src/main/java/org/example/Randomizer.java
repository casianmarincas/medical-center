package org.example;

import med.model.*;
import med.networking.RequestType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class Randomizer {

    public static List<Callable<Object>> getRequests(List<Person> personList, List<Treatment> treatmentList,
                                                     List<Location> locationList) {
        List<Callable<Object>> requests = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            RequestData requestData = Randomizer.getRandomAppointmentRequestData(personList, treatmentList, locationList);
            requests.add(new ClientRequest("127.0.0.1", 55555, requestData));
            Appointment appointment = (Appointment) requestData.getObject();
            Payment payment = new Payment(LocalDateTime.now(), appointment.getPerson().getCnp(),
                    appointment.getTreatment().getCost());
            RequestData addPaymentRequestData = new AddPaymentRequestData(RequestType.ADD_PAYMENT, payment);
            requests.add(new ClientRequest("127.0.0.1", 55555, addPaymentRequestData));
        }
        return requests;
    }

    public static RequestData getRandomAppointmentRequestData(List<Person> personList, List<Treatment> treatmentList, List<Location> locationList) {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(personList.size());
        Person person = personList.get(rand_int1);
        int rand_int2 = rand.nextInt(treatmentList.size());
        Treatment treatment = treatmentList.get(rand_int2);
        int rand_int3 = rand.nextInt(locationList.size());
        Location location = locationList.get(rand_int3);

        Appointment appointment = new Appointment(person, treatment, location, LocalDateTime.now());
        return new AppointmentRequestData(RequestType.ADD_APPOINTMENT, appointment);
    }

}
