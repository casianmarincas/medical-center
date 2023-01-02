package med.server;

import med.model.Appointment;
import med.model.Payment;
import med.persistence.repository.AppointmentRepo;
import med.persistence.repository.PaymentRepo;
import med.service.IService;

public class Service implements IService {

    private final AppointmentRepo appointmentRepo;
    private final PaymentRepo paymentRepo;

    public Service(AppointmentRepo appointmentRepo, PaymentRepo paymentRepo) {
        this.appointmentRepo = appointmentRepo;
        this.paymentRepo = paymentRepo;
    }

    @Override
    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepo.add(appointment);
    }

    @Override
    public Payment addPayment(Payment payment) {
        return paymentRepo.add(payment);
    }
}
