package med.server;

import med.model.*;
import med.persistence.repository.*;
import med.service.IService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
class AppointmentTimeComparator implements Comparator<Appointment> {
    @Override
    public int compare(Appointment a, Appointment b) {
        return a.getAppointmentDateTime().compareTo(b.getAppointmentDateTime());
    }
}
public class Service implements IService {

    private final AppointmentRepo appointmentRepo;
    private final PaymentRepo paymentRepo;
    private final LocationRepo locationRepo;
    private final TreatmentRepo treatmentRepo;
    private final TreatmentLocationRepo treatmentLocationRepo;
    private final PersonRepo personRepo;

    public Service(AppointmentRepo appointmentRepo, PaymentRepo paymentRepo, LocationRepo locationRepo, TreatmentLocationRepo treatmentLocationRepo, TreatmentRepo treatmentRepo, PersonRepo personRepo) {
        this.appointmentRepo = appointmentRepo;
        this.paymentRepo = paymentRepo;
        this.locationRepo = locationRepo;
        this.treatmentRepo = treatmentRepo;
        this.treatmentLocationRepo = treatmentLocationRepo;
        this.personRepo = personRepo;
    }

    @Override
    public Appointment addAppointment(Appointment appointment) {

        List<Appointment> appointmentList = appointmentRepo.getAll().stream()
                .filter(ap -> ap.getLocation().equals(appointment.getLocation()))
                .sorted(new AppointmentTimeComparator()).toList();

        int noOverlapping = 0;

        for (Appointment a: )


        return appointmentRepo.add(appointment);
    }

    @Override
    public Payment addPayment(Payment payment) {
        return paymentRepo.add(payment);
    }

    @Override
    public Appointment cancelAppointment(Appointment appointment) {
        System.out.println("DELETING\n" + appointment);
        return appointmentRepo.remove(appointment);
    }

    @Override
    public Payment cancelPayment(Payment payment) {
        Payment payment1 = new Payment(payment.getDate(),
                payment.getLocation(),
                payment.getTreatment(),
                payment.getPerson(),
                -payment.getSum());
        return paymentRepo.add(payment1);
    }

    public List<Location> getAllLocation() {
        return locationRepo.getAll();
    }

    public List<Person> getAllPerson() {
        return personRepo.getAll();
    }

    public List<Treatment> getAllTreatment() {
        return treatmentRepo.getAll();
    }

    public void verifySum() {
        List<Payment> paymentList = paymentRepo.getAll();
        List<Appointment> appointmentList = appointmentRepo.getAll();

        int paymentSum = 0;
        int appointmentSum = 0;

        for (Payment p : paymentList) {
            paymentSum += p.getSum();
        }

        for (Appointment a : appointmentList) {
            appointmentSum += a.getTreatment().getCost();
        }

        if (paymentSum != appointmentSum) {
            System.out.println("Soldul total nu corespunde " + paymentSum + appointmentSum);
        }

    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.getAll();
    }

    public List<Payment> getAllPayments() {
        return paymentRepo.getAll();
    }
}
