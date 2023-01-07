package org.example;

import med.model.*;
import med.networking.RequestType;
import med.networking.Response;
import med.networking.ResponseType;

import java.beans.IntrospectionException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class RequestsCreator extends Thread {

    private final ExecutorService executorService;
    private final Person person;
    private final List<Treatment> treatmentList;
    private final List<Location> locationList;

    public RequestsCreator(Person person, ExecutorService executorService, List<Treatment> treatmentList, List<Location> locationList) {
        this.person = person;
        this.executorService = executorService;
        this.treatmentList = treatmentList;
        this.locationList = locationList;
    }

    @Override
    public void run() {

        while (true) {
            RequestData addAppointmentRequestData = Randomizer.getRandomAppointmentRequestData(person, treatmentList, locationList);
            ClientRequest addAppointmentRequest = new ClientRequest("127.0.0.1", 55555, addAppointmentRequestData);

            Future<Object> resultAddAppointment = executorService.submit(addAppointmentRequest);


            boolean error=false;
            try {
                Response response = (Response) resultAddAppointment.get();
                if (response.type().equals(ResponseType.ERROR)){
                    error=true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (!error) {
                Appointment appointment = null;
                try {
                    appointment = (Appointment) ((Response) resultAddAppointment.get()).data();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Payment payment = new Payment(LocalDateTime.now(),
                        appointment.getLocation(),
                        appointment.getTreatment(),
                        appointment.getPerson(),
                        appointment.getTreatment().getCost());
                RequestData addPaymentRequestData = new RequestData(RequestType.ADD_PAYMENT, payment);

                ClientRequest addPaymentRequest = new ClientRequest("127.0.0.1", 55555, addPaymentRequestData);

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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


            }
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
