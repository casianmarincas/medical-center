package org.example;

import med.model.Appointment;
import med.model.Payment;
import med.networking.Request;
import med.networking.RequestType;
import med.networking.Response;
import med.networking.ResponseType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class ClientRequest implements Runnable {

    private Socket socket;
    private String ipAddress;
    private int port;

    private ObjectInputStream input;
    private ObjectOutputStream output;


    private RequestData requestData;

    public ClientRequest(String ipAddress, int port, RequestData requestData) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.requestData = requestData;

        initConnection();
    }

    private void initConnection() {
        try {
            socket = new Socket(ipAddress, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendRequest(Request request) {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error sending object " + e);
        }
    }

    private Response getResponse() {
        Response response;
        try {
            response =  (Response) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error sending object " + e);
        }
        return response;
    }

    public void run() {
        Appointment appointment = (Appointment) requestData.getObject();
        sendRequest(new Request.Builder().type(RequestType.ADD_APPOINTMENT).data(appointment).build());
        Response response = getResponse();
        if (response.type().equals(ResponseType.OK)){
            initConnection();
            System.out.println("Appointment facut cu succes!!!");
            Payment payment = new Payment(LocalDateTime.now(), appointment.getPerson().getCnp(), appointment.getTreatment().getCost());

            sendRequest(new Request.Builder().type(RequestType.ADD_PAYMENT).data(payment).build());
            Response response2 = getResponse();
            if (response2.type().equals(ResponseType.OK)) {
                System.out.println("Payment facut cu succes!!!");
            }

        }


    }
}