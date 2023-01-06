package med.server;

import med.model.Appointment;
import med.model.Location;
import med.model.Payment;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Verifier extends Thread {

    private final Service service;


    public Verifier(Service service) {
        this.service = service;
    }


    @Override
    public void run() {
        while (true) {
            System.out.println("I am verifying now");

            List<Appointment> appointmentList = service.getAllAppointments();
            Map<Location, List<Appointment>> locationAppointmentMap = new HashMap<>();
            for (Appointment appointment : appointmentList) {
                locationAppointmentMap.computeIfAbsent(appointment.getLocation(), k -> new ArrayList<>());
                locationAppointmentMap.get(appointment.getLocation()).add(appointment);
            }

            List<Payment> paymentList = service.getAllPayments();
            Map<Location, List<Payment>> locationPaymentMap = new HashMap<>();
            for (Payment payment : paymentList) {
                locationPaymentMap.computeIfAbsent(payment.getLocation(), k -> new ArrayList<>());
                locationPaymentMap.get(payment.getLocation()).add(payment);
            }

            List<Location> locations = service.getAllLocation();

            try {
                generateReport(locations, locationAppointmentMap, locationPaymentMap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void generateReport(List<Location> locations, Map<Location, List<Appointment>> locationAppointmentMap, Map<Location, List<Payment>> locationPaymentMap) throws IOException {

        FileWriter myWriter = new FileWriter("report.txt");

        for (Location l : locations) {
            myWriter.write(l.getName() + " at " + LocalDateTime.now() + '\n');
            double sold = 0;
            if (locationPaymentMap.containsKey(l)) {
                for (Payment p : locationPaymentMap.get(l)) {
                    sold += p.getSum();
                }
            }
            myWriter.write("SOLD: " + sold + '\n');
            myWriter.write("NOT PAID:\n");
            if (locationAppointmentMap.containsKey(l)) {
                for (Appointment appointment : locationAppointmentMap.get(l)) {
                    boolean found = false;
                    if (locationPaymentMap.containsKey(l)) {
                        List<Payment> paymentListTemporary = new ArrayList<>(locationPaymentMap.get(l));
                        for (Payment payment : paymentListTemporary) {
                            if (payment.getPerson().equals(appointment.getPerson()) &&
                                    payment.getSum() == appointment.getTreatment().getCost()) {
                                found = true;
                                paymentListTemporary.remove(payment);
                                break;
                            }

                        }
                    }
                    if (!found) {
                        myWriter.write(appointment.toString() + '\n');
                    }
                }
            }
            myWriter.write('\n');
        }
        myWriter.close();
        System.out.println("Successfully wrote to the file.");
    }
}
