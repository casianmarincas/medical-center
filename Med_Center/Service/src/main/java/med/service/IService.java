package med.service;

import med.model.*;

import java.util.List;

public interface IService {
    Appointment addAppointment(Appointment appointment);
    Payment addPayment(Payment payment);

    Appointment cancelAppointment(Appointment appointment);
    Payment cancelPayment(Payment payment);
    List<Location> getAllLocation();
    List<Person> getAllPerson();
    List<Treatment> getAllTreatment();

}
