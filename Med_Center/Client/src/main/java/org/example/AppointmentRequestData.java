package org.example;

import med.model.Appointment;
import med.networking.RequestType;

public class AppointmentRequestData extends RequestData{

    private Appointment appointment;

    public AppointmentRequestData(RequestType requestType, Appointment appointment) {
        super(requestType);
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public Object getObject() {
        return appointment;
    }
}
