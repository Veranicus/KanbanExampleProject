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

public class ProductionLineB implements ProductionLine {
    private Queue<GeneralTask> queuedTasksProductionLineA;

    private static final String productionLineName = "Production Line B";
    DelayUtil d = new DelayUtil();

    public ProductionLineB() {
    }

    public ProductionLineB(Queue<GeneralTask> queuedTasksProductionLineA) {
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
//
//    public void finishMultipleTasks(List<Text> listOfTaskForFinish, Controller controller, TaskPlanner taskPlanner) {
//        new Thread(() -> {
//            for (Text text : listOfTaskForFinish) {
//                try {
//                    long productionTime = (long) DelayUtil.getRandomDoubleBetweenRange(500, 1500);
//                    System.out.println(this.getClass().getSimpleName() + " Production time of " + text.getText()
//                            + " is " + productionTime);
//                    Thread.sleep(productionTime);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Platform.runLater(() -> controller.vBox3.getChildren().add(text));// Update on JavaFX Application Thread
//                System.out.println("Finished");
//            }
//        }).start();
//    }

    @Override
    public List<Text> processMultipleTasks(List<GeneralTask> TasksToStart, Controller controller,
                                           LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse,
                                           TaskPlanner taskPlanner) {

        List<Text> processedTasks = new ArrayList<>();

        for (int i = 0; i < TasksToStart.size(); i++) {
            GeneralTask generalTask = TasksToStart.get(i);
            readyMaterialsForOneTask(generalTask, localWarehouse, distantWarehouse, i, controller);
            System.out.println("\n************** " + generalTask.getName() + " " + i + " Have all required materials" +
                    " ***************");
            processedTasks.add((new Text(generalTask.getName())));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        TaskPlanner taskPlanner1 = new TaskPlanner(processedTasks, controller);
        executorService.execute(taskPlanner1);
        return processedTasks;
    }

    private void readyMaterialsForOneTask(GeneralTask oneGeneralTask, LocalWarehouse localWarehouse,
                                          DistantWarehouse distantWarehouse, int indexOfTask, Controller controller) {

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
                d.delay((long) waitingTime);
//                Platform.runLater(() -> controller.vBox3.getChildren().add(new Text(oneGeneralTask.getName())));
            }
        }
    }
}
