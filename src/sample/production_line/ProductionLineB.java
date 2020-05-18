package sample.production_line;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.text.Text;
import sample.Controller;
import sample.delay.DelayUtil;
import sample.material.AbsMaterial;
import sample.task.GeneralTask;
import sample.warehouse.DistantWarehouse;
import sample.warehouse.LocalWarehouse;

import java.util.*;

public class ProductionLineB implements ProductionLine {
    private Queue<GeneralTask> queuedTasksProductionLineA;

    Thread shower;
    Thread warehosueCalculator;

    DistantWarehouse distantWarehouse = new DistantWarehouse();

    AbsMaterial neededMaterial;

    int quantityNeeded;

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

    @Override
    public synchronized List<Text> processMultipleTasks(List<GeneralTask> tasksToStart, Controller controller,
                                                        LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse,
                                                        TaskPlanner taskPlanner) {

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                for (int i = 0; i < tasksToStart.size(); i++) {
                    readyMaterialsForOneTask(tasksToStart.get(i), localWarehouse, distantWarehouse, i, controller);
                    try {
                        System.out.println("\n************** " + tasksToStart.get(i).getName() + " " + i + " " +
                                "Have all required materials" + " ***************");
                        double timeToWait = DelayUtil.getRandomDoubleBetweenRange(2000, 2500);
                        System.out.println("Making " + tasksToStart.get(i).getName() + " " + i + " for " + timeToWait / 1000 + " seconds");
                        Thread.sleep((long) timeToWait);
                        Platform.runLater(() -> controller.vBox3.getChildren().add(new Text(tasksToStart.get(1).getName())));
                    } catch (InterruptedException E) {
                        E.printStackTrace();
                    }
                }
                return null;
            }
        };
        shower = new Thread(task);
        shower.start();

        return new ArrayList<>();
    }

    Task task1 = new Task<Void>() {
        @Override
        public Void call() {
            try {
                long waitingTime = (long) DelayUtil.getRandomDoubleBetweenRange(6000, 8000);
                System.out.println("Local warehouse is out of stock for material " + neededMaterial.getName());
                System.out.println("Contacting distant warehouse to get required items");
                distantWarehouse.provideMultipleMaterials(neededMaterial,
                        quantityNeeded);
                System.out.println("Waiting time: " + waitingTime / 1000 + " seconds.");
                Thread.sleep(waitingTime);
            } catch (InterruptedException E) {
                E.printStackTrace();
            }
            return null;
        }
    };

    private synchronized Thread readyMaterialsForOneTask(GeneralTask oneGeneralTask, LocalWarehouse localWarehouse,
                                                         DistantWarehouse distantWarehouse, int indexOfTask, Controller controller) {

        Iterator hmIterator = oneGeneralTask.getMaterialsRequired().entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry<AbsMaterial, Integer> mapElement = (Map.Entry) hmIterator.next();
            System.out.println(this.getClass().getSimpleName() + " wants " + mapElement.getKey().getName() + " in" +
                    " quantity " + mapElement.getValue() + " for task " + oneGeneralTask.getName() + " " + indexOfTask);
            Integer providedQuantity = localWarehouse.provideMultipleMaterials(mapElement.getKey(),
                    mapElement.getValue());
            if (providedQuantity < mapElement.getValue()) {
                neededMaterial = mapElement.getKey();
                quantityNeeded = mapElement.getValue() - providedQuantity;
//                Task task = new Task<Void>() {
//                    @Override
//                    public Void call() {
//                        try {
//                            long waitingTime = (long) DelayUtil.getRandomDoubleBetweenRange(1000, 1500);
//                            System.out.println("Local warehouse is out of stock for material " + mapElement.getKey().getName());
//                            System.out.println("Contacting distant warehouse to get required items");
//                            neededMaterial = mapElement.getKey();
//                            quantityNeeded = mapElement.getValue() - providedQuantity;
//                            distantWarehouse.provideMultipleMaterials(mapElement.getKey(),
//                                    mapElement.getValue() - providedQuantity);
//                            System.out.println("Waiting time: " + waitingTime / 1000 + " seconds.");
//                            Thread.sleep(waitingTime);
//                        } catch (InterruptedException E) {
//                            E.printStackTrace();
//                        }
//                        return null;
//                    }
//                };
//                System.out.println("Local warehouse is out of stock for material " + mapElement.getKey().getName());
//                System.out.println("Contacting distant warehouse to get required items");
//                distantWarehouse.setAbsMaterial(mapElement.getKey());
//                distantWarehouse.setNumberOfItems(mapElement.getValue() - providedQuantity);
//                distantWarehouse.provideMultipleMaterials(mapElement.getKey(),
//                        mapElement.getValue() - providedQuantity);
//                double waitingTime = DelayUtil.getRandomDoubleBetweenRange(2000, 2500);
//                new Thread(() -> {
//                    try {
//                        System.out.println(". Waiting time: " + waitingTime / 1000 + " seconds.");
//                        Thread.sleep((long) waitingTime);
//                    } catch (InterruptedException e) {
//                    }
//                }).start();
                warehosueCalculator = new Thread(task1);
                warehosueCalculator.start();
//                warehosueCalculator.start();

            }
        }
        return warehosueCalculator;
    }
}

