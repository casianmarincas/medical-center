package med.server;

import med.service.IService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    private IService service;
    private int port;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    private ServerSocket serverSocket;

    public Server(IService service, int port) {
        this.service = service;
        this.port = port;
    }

    public void start() {
        try {
            System.out.println("Starting Server");
            serverSocket = new ServerSocket(port);

            while (true) {
                System.out.println("Waiting for request");
                try {
                    Socket s = serverSocket.accept();
                    System.out.println("Processing request");
                    executorService.submit(new ServiceRequest(s, service));
                } catch (IOException ioe) {
                    System.out.println("Error accepting connection");
//                    ioe.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error starting Server on " + port);
            e.printStackTrace();
        }

    }

    public void stop() {
        executorService.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("aruncam exceptie");
        }
    }

    public void stopExecutorService() {
        executorService.shutdown();
    }

}
