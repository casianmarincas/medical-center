package org.example;

import med.model.*;
import med.networking.Request;
import med.networking.RequestType;
import med.networking.Response;
import med.networking.ResponseType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Callable;

public class GetAllRequest implements Callable<String> {

    private Socket socket;
    private String ipAddress;
    private int port;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private List<Person> personList;
    private List<Treatment> treatmentList;
    private List<Location> locationList;


    public GetAllRequest(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
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
            response = (Response) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error sending object " + e);
        }
        return response;
    }


    public List<Location> getLocationList(){
        return locationList;
    }

    public List<Treatment> getTreatmentList(){
        return treatmentList;
    }
    public List<Person> getPersonList(){
        return personList;
    }

    @Override
    public String call() {
        sendRequest(new Request.Builder().type(RequestType.GET_ALL_LOCATION).build());
        Response response = getResponse();
        this.locationList = (List<Location>) response.data();
        if (response.type().equals(ResponseType.OK)) {
            initConnection();
            sendRequest(new Request.Builder().type(RequestType.GET_ALL_TREATMENT).build());
            Response response2 = getResponse();
            this.treatmentList = (List<Treatment>) response2.data();

            if (response2.type().equals(ResponseType.OK)) {
                initConnection();
                sendRequest(new Request.Builder().type(RequestType.GET_ALL_PERSON).build());
                Response response3 = getResponse();
                this.personList = (List<Person>) response3.data();
            }

        }

        return "ok";
    }

}