package sample.production_line;

import sample.Controller;
import sample.delay.DelayUtil;
import sample.task.GeneralTask;
import sample.warehouse.DistantWarehouse;
import sample.warehouse.LocalWarehouse;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

public class ProductionLineA implements ProductionLine {

    private Queue<GeneralTask> queuedTasksProductionLineA;

    private static final String productionLineName = "Production Line A";
    DelayUtil d = new DelayUtil();

    private List<GeneralTask> TasksToStart;
    private Controller controller;
    private LocalWarehouse localWarehouse;
    private DistantWarehouse distantWarehouse;
    private TaskPlanner taskPlanner;
    private GeneralTask onetaskToStart;

    public ProductionLineA(Queue<GeneralTask> queuedTasksProductionLineA, DelayUtil d, List<GeneralTask> tasksToStart,
                           Controller controller, LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse,
                           TaskPlanner taskPlanner, GeneralTask onetaskToStart) {
        this.queuedTasksProductionLineA = queuedTasksProductionLineA;
        this.d = d;
        TasksToStart = tasksToStart;
        this.controller = controller;
        this.localWarehouse = localWarehouse;
        this.distantWarehouse = distantWarehouse;
        this.taskPlanner = taskPlanner;
        this.onetaskToStart = onetaskToStart;
    }

    public ProductionLineA() {
    }

    public ProductionLineA(Queue<GeneralTask> queuedTasksProductionLineA) {
        this.queuedTasksProductionLineA = queuedTasksProductionLineA;
    }

    public Queue<GeneralTask> getQueuedTasksProductionLineA() {
        return queuedTasksProductionLineA;
    }

    public void setQueuedTasksProductionLineA(Queue<GeneralTask> queuedTasksProductionLineA) {
        this.queuedTasksProductionLineA = queuedTasksProductionLineA;
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
                pool.submit(new TaskDisplay(generalTaskToShow, controller));
            }
            Instant end = Instant.now();
            System.out.println("***** Total Time to proces" + tasksWithMaterialsToFinish.get(1).getName() + " Group of tasks is " +
                    Duration.between(start, end).toMillis() + "********");
        } catch (Exception E) {
            E.printStackTrace();
        }
    }


}
