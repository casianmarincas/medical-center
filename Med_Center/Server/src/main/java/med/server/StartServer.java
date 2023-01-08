package med.server;

import med.model.Location;
import med.model.Treatment;
import med.model.TreatmentLocation;
import med.persistence.repository.*;

import java.util.List;

import static java.lang.Thread.sleep;

public class StartServer {

    public static void main(String[] args) {
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        LocationRepo locationRepo = new LocationRepo();
        PaymentRepo paymentRepo = new PaymentRepo();
        PersonRepo personRepo = new PersonRepo();
        TreatmentRepo treatmentRepo = new TreatmentRepo();
        TreatmentLocationRepo treatmentLocationRepo = new TreatmentLocationRepo();

        Service service = new Service(appointmentRepo, paymentRepo, locationRepo, treatmentLocationRepo, treatmentRepo, personRepo);

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
//        locationRepo.add(new Location("Regina Maria3"));
//        locationRepo.add(new Location("Regina Maria4"));
//
//        List<Location> locationList = locationRepo.getAll();
//        int[][] matrix = new int[4][5];
//        matrix[0][0]=3;
//        matrix[0][1]=1;
//        matrix[0][2]=1;
//        matrix[0][3]=2;
//        matrix[0][4]=1;
//        for(int i=1; i<4;i++){
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
//

        int chatServerPort = 55555;

        System.out.println("Starting server on port: " + chatServerPort);
        Server server = new Server(service, chatServerPort);
        try {
            Verifier verifier = new Verifier(service);
            verifier.start();

            Thread t = new Thread(server::start);

            t.start();
            sleep(30000);

            System.out.println("Calling stop!");
            server.stop();
            t.join();
            System.out.println("Server stopped");

            verifier.stop();
            verifier.join();
        } catch (RuntimeException e) {
            System.err.println("Error starting the server" + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Final yay");
    }
}
