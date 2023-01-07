package med.server;

import med.model.Location;
import med.model.Treatment;
import med.model.TreatmentLocation;
import med.persistence.repository.*;

import java.util.List;

import static java.lang.Thread.sleep;

public class StartServer {

    private static int defaultPort = 55555;

    public static void main(String[] args) {
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        LocationRepo locationRepo = new LocationRepo();
        PaymentRepo paymentRepo = new PaymentRepo();
        PersonRepo personRepo = new PersonRepo();
        TreatmentRepo treatmentRepo = new TreatmentRepo();
        TreatmentLocationRepo treatmentLocationRepo= new TreatmentLocationRepo();

        Service service = new Service(appointmentRepo, paymentRepo, locationRepo, treatmentLocationRepo, treatmentRepo, personRepo);
//
//
//        treatmentRepo.add(new Treatment(50, 120));
//        treatmentRepo.add(new Treatment(20, 20));
//        treatmentRepo.add(new Treatment(40, 30));
//        treatmentRepo.add(new Treatment(100, 60));
//        treatmentRepo.add(new Treatment(30, 30));
//
//        List<Treatment> treatmentList= treatmentRepo.getAll();
//
//        locationRepo.add(new Location("Regina Maria1"));
//        locationRepo.add(new Location("Regina Maria2"));
//
//        List<Location> locationList = locationRepo.getAll();
//        int[][] matrix = new int[2][5];
//        matrix[0][0]=3;
//        matrix[0][1]=1;
//        matrix[0][2]=1;
//        matrix[0][3]=2;
//        matrix[0][4]=1;
//        for(int i=1; i<2;i++){
//            for(int j=0; j<5;j++){
//                matrix[i][j]=matrix[0][j]*(i-1);
//            }
//        }
//
//        int i=0;
//        int j=0;
//        for (Treatment t: treatmentList) {
//            i=0;
//            for (Location l:locationList){
//                treatmentLocationRepo.add(new TreatmentLocation(t,l, matrix[i][j]));
//                i++;
//            }
//            j++;
//        }


        int chatServerPort = defaultPort;

        System.out.println("Starting server on port: " + chatServerPort);
        Server server = new Server(service, chatServerPort);
        try {
            Verifier t = new Verifier(service);
            t.start();

            server.start();
            sleep(2000);

            server.stopExecutorService();
            server.stop();
            System.out.println("Server stopped");
            server.join();
            System.out.println("Server joined");

            t.stop();
            System.out.println("T stopped");
            t.join();
            System.out.println("T joined");

/*
            server.setRunning(false);
            t.setRunning(false);
*/
//
//            server.join();
//            t.join();
        } catch (RuntimeException e) {
            System.err.println("Error starting the server" + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                server.stop();
            } catch (RuntimeException e) {
                System.err.println("Error stopping server " + e.getMessage());
            }
        }
    }
}
