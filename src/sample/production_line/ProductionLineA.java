package sample.production_line;

import javafx.scene.text.Text;
import sample.Controller;
import sample.delay.DelayUtil;
import sample.material.AbsMaterial;
import sample.task.GeneralTask;
import sample.warehouse.DistantWarehouse;
import sample.warehouse.LocalWarehouse;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public void processMultipleTasks(List<GeneralTask> TasksToStart, Controller controller,
                                     LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse) {

        List<Text> processedTasks = new ArrayList<>();

        for (int i = 0; i < TasksToStart.size(); i++) {
            GeneralTask generalTask = TasksToStart.get(i);
            readyMaterialsForOneTask(generalTask, localWarehouse, distantWarehouse, i);
            System.out.println("\n************** " + generalTask.getName() + " " + i + " Have all required materials" +
                    " ***************");
            processedTasks.add((new Text(generalTask.getName())));
        }

//        finishMultipleTasks(processedTasks, controller);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        TaskPlanner taskPlanner1 = new TaskPlanner(processedTasks, controller);
//        executorService.execute(taskPlanner1);
//        taskPlanner.finishMultipleTasks(processedTasks, controller);
//        return processedTasks;
    }

    private void readyMaterialsForOneTask(GeneralTask oneGeneralTask, LocalWarehouse localWarehouse,
                                          DistantWarehouse distantWarehouse, int indexOfTask) {

        Iterator hmIterator = oneGeneralTask.getMaterialsRequired().entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry<AbsMaterial, Integer> mapElement = (Map.Entry) hmIterator.next();
            System.out.println(this.getClass().getSimpleName() + " wants " + mapElement.getKey().getName() + " in" +
                    " quantity " + mapElement.getValue() + " for task " + oneGeneralTask.getName() + " " + indexOfTask);
            Integer providedQuantity = localWarehouse.provideMultipleMaterials(mapElement.getKey(),
                    mapElement.getValue());
            if (providedQuantity < mapElement.getValue()) {
                System.out.println("Local warehouse is out of stock for material " + mapElement.getKey().getName());
                System.out.println("Contacting distant warehouse to get required items");
                distantWarehouse.setAbsMaterial(mapElement.getKey());
                distantWarehouse.setNumberOfItems(mapElement.getValue() - providedQuantity);
                distantWarehouse.provideMultipleMaterials(mapElement.getKey(),
                        mapElement.getValue() - providedQuantity);
                double waitingTime = DelayUtil.getRandomDoubleBetweenRange(1000, 1500);
                System.out.println(". Waiting time: " + waitingTime / 1000 + " seconds.");
            }
        }
    }

}
