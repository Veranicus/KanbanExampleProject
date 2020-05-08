package sample.production_line;

import javafx.scene.text.Text;
import sample.Controller;
import sample.material.AbsMaterial;
import sample.material.Salt;
import sample.task.GeneralTask;
import sample.task.MakeBread;
import sample.task.TaskProduct;
import sample.warehouse.LocalWarehouse;
import sample.warehouse.Warehouse;

import java.util.*;

public class ProductionLineA implements ProductionLine {

    private Queue<GeneralTask> queuedTasksProductionLineA;

    private static final String productionLineName = "Production Line A";

    Warehouse warehouse = new LocalWarehouse();

//    Controller controller = new Controller();

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
    public List<TaskProduct> processTask(GeneralTask generalTaskToStart) {
        return null;
    }

    @Override
    public List<TaskProduct> processMultipleMakeBreadTasks(List<MakeBread> makeBreadTasksToStart, Controller controller,
                                                           Warehouse localWarehouse) {

        List<TaskProduct> taskProducts = new ArrayList<>();
        for (int i = 0; i < makeBreadTasksToStart.size(); i++) {
            MakeBread makeBread = makeBreadTasksToStart.get(i);
            Iterator hmIterator = makeBread.getMaterialsRequired().entrySet().iterator();
            while (hmIterator.hasNext()) {
                Map.Entry<AbsMaterial, Integer> mapElement = (Map.Entry) hmIterator.next();
                System.out.println(this.getClass().getSimpleName() + " wants " + mapElement.getKey().getName() + " in" +
                        " quantity " + mapElement.getValue());
                Integer providedQuantity = localWarehouse.provideMultipleMaterials(mapElement.getKey(),
                        mapElement.getValue());
                if (providedQuantity == 0) {
                    System.out.println("Local warehouse is out of stock for material "  + mapElement.getKey().getName());
                    System.out.println("Contacting distant warehouse to get required items");
                    break;
                }
            }
//            makeBread.getMaterialsRequired().forEach((key, value) -> {
//                        localWarehouse.provideQuantityOfOneMaterial(key)
//                        System.out.println(key.getName() + value));
//                    }

            controller.vBox3.getChildren().add(new Text(makeBread.getName()));
        }
        return taskProducts;
    }


}
