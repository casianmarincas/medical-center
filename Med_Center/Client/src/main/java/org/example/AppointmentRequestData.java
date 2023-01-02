package org.example;

import med.model.Appointment;

public class AppointmentRequestData extends RequestData{

    private Appointment appointment;

    public AppointmentRequestData(Appointment appointment) {
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
