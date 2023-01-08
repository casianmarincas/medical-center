package org.example;

import med.model.Appointment;
import med.model.Payment;
import med.networking.Request;
import med.networking.RequestType;
import med.networking.Response;
import med.networking.ResponseType;

import javax.management.RuntimeErrorException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;

public class ClientRequest implements Callable<Object> {

    private Socket socket;
    private final String ipAddress;
    private final int port;

    private ObjectInputStream input;
    private ObjectOutputStream output;


    private final RequestData requestData;

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
            System.out.println("I stopped!!");
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

    @Override
    public Object call() {

        Response response = null;
        try {
            if (requestData.getType().equals(RequestType.ADD_APPOINTMENT)) {
                Appointment appointment = (Appointment) requestData.getObject();
                sendRequest(new Request.Builder().type(RequestType.ADD_APPOINTMENT).data(appointment).build());
                response = getResponse();
            }

            if (requestData.getType().equals(RequestType.ADD_PAYMENT)) {
                Payment payment = (Payment) requestData.getObject();
                sendRequest(new Request.Builder().type(RequestType.ADD_PAYMENT).data(payment).build());
                response = getResponse();
            }

            if (requestData.getType().equals(RequestType.CANCEL_APPOINTMENT)) {
                Appointment appointment = (Appointment) requestData.getObject();
                sendRequest(new Request.Builder().type(RequestType.CANCEL_APPOINTMENT).data(appointment).build());
                response = getResponse();
            }

            if (requestData.getType().equals(RequestType.CANCEL_PAYMENT)) {
                Payment payment = (Payment) requestData.getObject();
                sendRequest(new Request.Builder().type(RequestType.CANCEL_PAYMENT).data(payment).build());
                response = getResponse();
            }

            if (response.type().equals(ResponseType.OK)) {
                System.out.println("OK!!!");
            }

            if (response.type().equals(ResponseType.ERROR)) {

                System.out.println("Nu s-a putut face cererea");

            }
        } catch (RuntimeException runtimeException) {

        }

        return response;
    }
}