package sample.production_line;

import sample.Controller;
import sample.task.GeneralTask;
import sample.warehouse.DistantWarehouse;
import sample.warehouse.LocalWarehouse;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ProductionLineB implements ProductionLine {

    //https://stackoverflow.com/questions/289434/how-to-make-a-java-thread-wait-for-another-threads-output
    //https://stackoverflow.com/questions/289434/how-to-make-a-java-thread-wait-for-another-threads-output
    //https://stackoverflow.com/questions/13784333/platform-runlater-and-task-in-javafx
    //https://stackoverflow.com/questions/61854896/javafx-application-doesnt-add-elements-to-gui-after-finishing-tasks-from-differ


    private static final String productionLineName = "Production Line B";

    public ProductionLineB() {
    }

    public static String getProductionLineName() {
        return productionLineName;
    }

    @Override
    public void processMultipleTasks(List<GeneralTask> tasksWithMaterialsToFinish, Controller controller,
                                     LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse, ExecutorService pool) {
        Instant start = Instant.now();
        // Build a fixed number of thread pool
        try {
            for (GeneralTask g : tasksWithMaterialsToFinish) {
                GeneralTask generalTaskToCalculate = pool.submit(new ResourceCalculator(tasksWithMaterialsToFinish, controller,
                        localWarehouse, distantWarehouse, g)).get();
                GeneralTask generalTaskToShow = pool.submit(new TaskPlanner(generalTaskToCalculate, controller)).get();
                pool.submit(new TaskDisplay(generalTaskToShow, controller)).isDone();
            }
            Instant end = Instant.now();
            System.out.println("***** Total Time to proces" + tasksWithMaterialsToFinish.get(0).getName() + " Group of tasks is " +
                    Duration.between(start, end).toMillis() + "********");
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
}

