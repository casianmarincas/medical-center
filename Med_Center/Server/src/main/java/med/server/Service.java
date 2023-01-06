package med.server;

import med.model.*;
import med.persistence.repository.*;
import med.service.IService;

import java.util.List;

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

//        List<TreatmentLocation> treatmentLocations = treatmentLocationRepo.getAll();
//        int nMax = 0;
//        TreatmentLocation tlFound = null;
//        for (TreatmentLocation tl : treatmentLocations) {
//            if (tl.getLocation().equals(appointment.getLocation()) &&
//                    tl.getTreatment().equals(appointment.getTreatment())) {
//                tlFound = tl;
//                nMax = tl.getNrMax();
//            }
//        }
//
//        if (nMax > 0) {
//            tlFound.setNrMax(tlFound.getNrMax() - 1);
////            treatmentLocationRepo.update(tl);
//
//            return appointmentRepo.add(appointment);
//        }
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
