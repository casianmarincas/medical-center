package med.server;

import med.persistence.repository.*;

public class StartServer {

    private static int defaultPort = 55555;

    public static void main(String[] args) {
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        LocationRepo locationRepo = new LocationRepo();
        PaymentRepo paymentRepo = new PaymentRepo();
        PersonRepo personRepo = new PersonRepo();
        TreatmentRepo treatmentRepo = new TreatmentRepo();

        Service service = new Service(appointmentRepo, paymentRepo);

        int chatServerPort = defaultPort;

        System.out.println("Starting server on port: " + chatServerPort);
        Server server = new Server(service, chatServerPort);
        try {
            server.start();
        } catch (RuntimeException e) {
            System.err.println("Error starting the server" + e.getMessage());
        } finally {
            try {
                server.stop();
            } catch (RuntimeException e) {
                System.err.println("Error stopping server " + e.getMessage());
            }
        }
    }
}
