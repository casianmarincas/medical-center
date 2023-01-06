package med.server;

import med.model.*;
import med.networking.Request;
import med.networking.RequestType;
import med.networking.Response;
import med.networking.ResponseType;
import med.service.IService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

class ServiceRequest implements Runnable {

    private final Socket socket;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    private IService service;

    public ServiceRequest(Socket connection, IService service) {
        this.socket = connection;
        this.service = service;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Object request = input.readObject();
            Response response = handleRequest((Request) request);
            if (response != null) {
                sendResponse(response);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            socket.close();
        } catch (IOException ioe) {
            System.out.println("Error closing client connection");
        }
    }

    private static final Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request) {
        Response response = null;

        try {
            if (request.type().equals(RequestType.ADD_APPOINTMENT)) {
                System.out.println("Am primit request add appointment");
                Appointment appointment = (Appointment) request.data();
                Appointment responseAppointment = service.addAppointment(appointment);
                response = new Response.Builder().type(ResponseType.OK).data(responseAppointment).build();
            }

            if (request.type().equals(RequestType.ADD_PAYMENT)) {
                System.out.println("Am primit request add payment");
                Payment payment = (Payment) request.data();
                Payment responsePayment = service.addPayment(payment);
                response = new Response.Builder().type(ResponseType.OK).data(responsePayment).build();
            }

            if (request.type().equals(RequestType.CANCEL_APPOINTMENT)) {
                System.out.println("Am primit request cancel appointment");
                Appointment appointment = (Appointment) request.data();
                Appointment responseAppointment = service.cancelAppointment(appointment);
                response = new Response.Builder().type(ResponseType.OK).data(responseAppointment).build();
            }

            if (request.type().equals(RequestType.CANCEL_PAYMENT)) {
                System.out.println("Am primit request cancel payment");
                Payment payment = (Payment) request.data();
                Payment responsePayment = service.cancelPayment(payment);
                response = new Response.Builder().type(ResponseType.OK).data(responsePayment).build();
            }

            if (request.type().equals(RequestType.GET_ALL_LOCATION)) {
                System.out.println("Am primit request get all location");
                List<Location> responseList = service.getAllLocation();
                response = new Response.Builder().type(ResponseType.OK).data(responseList).build();
            }

            if (request.type().equals(RequestType.GET_ALL_TREATMENT)) {
                System.out.println("Am primit request get all treatment");
                List<Treatment> responseList = service.getAllTreatment();
                response = new Response.Builder().type(ResponseType.OK).data(responseList).build();
            }

            if (request.type().equals(RequestType.GET_ALL_PERSON)) {
                System.out.println("Am primit request get all person");
                List<Person> responseList = service.getAllPerson();
                response = new Response.Builder().type(ResponseType.OK).data(responseList).build();
            }

        } catch (Error e){
            response = new Response.Builder().type(ResponseType.ERROR).build();
        }
        return response;
    }

    private void sendResponse(Response response) {
        System.out.println("sending response " + response);
        try {
            output.writeObject(response);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}