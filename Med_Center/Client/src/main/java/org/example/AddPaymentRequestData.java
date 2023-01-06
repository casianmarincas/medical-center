package org.example;

import med.model.Appointment;
import med.model.Payment;
import med.networking.RequestType;

public class AddPaymentRequestData extends RequestData {

    private Payment payment;

    public AddPaymentRequestData(RequestType requestType, Payment payment) {
        super(requestType);
        this.payment = payment;
    }

    @Override
    public Object getObject() {
        return payment;
    }
}
