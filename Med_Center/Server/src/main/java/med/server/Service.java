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
        return appointmentRepo.add(appointment);
    }

    @Override
    public Payment addPayment(Payment payment) {
        return paymentRepo.add(payment);
    }

    public List<Location> getAllLocation(){
        return locationRepo.getAll();
    }

    public List<Person> getAllPerson(){
        return personRepo.getAll();
    }

    public List<Treatment> getAllTreatment(){
        return treatmentRepo.getAll();
    }

    public void verifySum(){
        List<Payment> paymentList = paymentRepo.getAll();
        List<Appointment> appointmentList = appointmentRepo.getAll();

        int paymentSum = 0;
        int appointmentSum = 0;

        for (Payment p: paymentList){
            paymentSum+=p.getSum();
        }

        for (Appointment a: appointmentList){
            appointmentSum+=a.getTreatment().getCost();
        }

        if (paymentSum!=appointmentSum){
            System.out.println("Soldul total nu corespunde " + paymentSum + appointmentSum);
        }




    }


}
