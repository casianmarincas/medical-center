
import persistence.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static final int N_CLIENTS = 10;
    private static final ExecutorService exec= Executors.newFixedThreadPool(N_CLIENTS);
    public static void main(String[] args) throws InterruptedException {
//        AppointmentRepo appointmentRepo = new AppointmentRepo();
//        LocationRepo locationRepo = new LocationRepo();
//        PaymentRepo paymentRepo = new PaymentRepo();
//        PersonRepo personRepo = new PersonRepo();
//        TreatmentRepo treatmentRepo = new TreatmentRepo();


        Callable<String> callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(300);
            return "Task's execution";
        };

        List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);

        List<Future<String>> futures = exec.invokeAll(callableTasks);

    }
}
