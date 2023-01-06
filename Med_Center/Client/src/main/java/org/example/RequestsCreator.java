package org.example;

import med.model.*;
import med.networking.RequestType;
import med.networking.Response;

import java.beans.IntrospectionException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class RequestsCreator extends Thread {

    private final ExecutorService executorService;

    private final List<Person> personList;
    private final List<Treatment> treatmentList;
    private final List<Location> locationList;

    public RequestsCreator(ExecutorService executorService, List<Person> personList, List<Treatment> treatmentList, List<Location> locationList) {
        this.executorService = executorService;
        this.personList = personList;
        this.treatmentList = treatmentList;
        this.locationList = locationList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            RequestData addAppointmentRequestData = Randomizer.getRandomAppointmentRequestData(personList, treatmentList, locationList);
            ClientRequest addAppointmentRequest = new ClientRequest("127.0.0.1", 55555, addAppointmentRequestData);

            Future<Object> resultAddAppointment = executorService.submit(addAppointmentRequest);

            Appointment appointment = null;
            try {
                appointment = (Appointment) ((Response) resultAddAppointment.get()).data();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            Payment payment = new Payment(LocalDateTime.now(),
                    appointment.getLocation(),
                    appointment.getTreatment(),
                    appointment.getPerson().getCnp(),
                    appointment.getTreatment().getCost());
            RequestData addPaymentRequestData = new RequestData(RequestType.ADD_PAYMENT, payment);

            ClientRequest addPaymentRequest = new ClientRequest("127.0.0.1", 55555, addPaymentRequestData);

            Future<Object> resultAddPayment = executorService.submit(addPaymentRequest);

            try {
                payment = (Payment) ((Response) resultAddPayment.get()).data();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            Random r = new Random();
            if (r.nextDouble() < 0.25) {
                RequestData cancelPaymentRequestData = new RequestData(RequestType.CANCEL_PAYMENT, payment);
                ClientRequest cancelPaymentRequest = new ClientRequest("127.0.0.1", 55555, cancelPaymentRequestData);
                executorService.submit(cancelPaymentRequest);

                RequestData cancelAppointmentRequestData = new RequestData(RequestType.CANCEL_APPOINTMENT, appointment);
                ClientRequest cancelAppointmentRequest = new ClientRequest("127.0.0.1", 55555, cancelAppointmentRequestData);
                executorService.submit(cancelAppointmentRequest);
            }

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
