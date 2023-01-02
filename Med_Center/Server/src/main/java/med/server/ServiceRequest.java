package med.server;

import med.model.Appointment;
import med.networking.Request;
import med.networking.RequestType;
import med.networking.Response;
import med.networking.ResponseType;
import med.service.IService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

        if (request.type().equals(RequestType.ADD_APPOINTMENT)) {
            System.out.println("Am primit request add appointment");
            Appointment appointment = (Appointment) request.data();
            Appointment responseAppointment = service.addAppointment(appointment);
            sendResponse(new Response.Builder().type(ResponseType.OK).data(responseAppointment).build());
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