package org.example;

import med.networking.Request;
import med.networking.RequestType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    public void run() {
        sendRequest(new Request.Builder().type(RequestType.ADD_APPOINTMENT).data(requestData.getObject()).build());

    }
}