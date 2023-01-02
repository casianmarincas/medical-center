package med.service;

import med.model.Appointment;
import med.model.Payment;

public interface IService {
    Appointment addAppointment(Appointment appointment);
    Payment addPayment(Payment payment);
}
