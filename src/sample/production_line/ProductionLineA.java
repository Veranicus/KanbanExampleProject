package sample.production_line;

import javafx.scene.text.Text;
import sample.Controller;
import sample.material.Salt;
import sample.task.GeneralTask;
import sample.task.MakeBread;
import sample.task.TaskProduct;
import sample.warehouse.LocalWarehouse;
import sample.warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

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
            if (localWarehouse.provideQuantityOfOneMaterial(new Salt()) >=
                    makeBreadTasksToStart.get(i).getMaterialsRequired().get(new Salt()));
            System.out.println(makeBread);
            controller.vBox3.getChildren().add(new Text(makeBread.getName()));
        }
        return taskProducts;
    }


}
