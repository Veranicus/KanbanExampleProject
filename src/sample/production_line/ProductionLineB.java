package sample.production_line;

import sample.Controller;
import sample.task.GeneralTask;
import sample.warehouse.DistantWarehouse;
import sample.warehouse.LocalWarehouse;

import java.time.Duration;
import java.time.Instant;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class ProductionLineB implements ProductionLine, Callable<Object> {

    private Queue<GeneralTask> oneTaskQueue;
    private Controller controller;
    private LocalWarehouse localWarehouse;
    private DistantWarehouse distantWarehouse;
    private ExecutorService pool;


    private static final String productionLineName = "Production Line A";

    public ProductionLineB() {
    }

    public void setOneTaskQueue(Queue<GeneralTask> oneTaskQueue) {
        this.oneTaskQueue = oneTaskQueue;
    }

    public void addOneTask(GeneralTask oneTask) {
        this.oneTaskQueue.add(oneTask);
    }

    public ProductionLineB(Queue<GeneralTask> oneTaskQueue, Controller controller,
                           LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse) {
        this.oneTaskQueue = oneTaskQueue;
        this.controller = controller;
        this.localWarehouse = localWarehouse;
        this.distantWarehouse = distantWarehouse;
    }

    public static String getProductionLineName() {
        return productionLineName;
    }

    @Override
    public Object call() throws Exception {
        processOneTask(oneTaskQueue, controller, localWarehouse, distantWarehouse);
        return null;
    }

    @Override
    public void processOneTask(Queue<GeneralTask> tasksWithMaterialsToFinish, Controller controller,
                               LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse) {
        Instant start = Instant.now();
        // Build a fixed number of thread pool
        try {
            while (tasksWithMaterialsToFinish.iterator().hasNext()) {
                System.out.println(this.getClass().getSimpleName() + " IS MAKING " +
                        tasksWithMaterialsToFinish.peek().getName());
                GeneralTask generalTaskToCalculate = new ResourceCalculator(
                        this.controller, this.localWarehouse, this.distantWarehouse, tasksWithMaterialsToFinish.poll(),
                        controller.vBox21, controller.vBox31).call();
                GeneralTask generalTaskToShow = new TaskPlanner(generalTaskToCalculate, controller).call();
                new TaskDisplay(generalTaskToShow, controller, controller.vBox31).call();
            }
        } catch (Exception F) {
            F.printStackTrace();
            Instant end = Instant.now();
            System.out.println("***** Total Time to proces" + tasksWithMaterialsToFinish + " Group of tasks is " +
                    Duration.between(start, end).toMillis() + "********");
        }
    }
}
