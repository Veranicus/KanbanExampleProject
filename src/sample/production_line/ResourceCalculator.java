package sample.production_line;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.Controller;
import sample.delay.DelayUtil;
import sample.material.AbsMaterial;
import sample.task.GeneralTask;
import sample.warehouse.DistantWarehouse;
import sample.warehouse.LocalWarehouse;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;

public class ResourceCalculator implements Callable<GeneralTask> {


    private Controller controller;
    private LocalWarehouse localWarehouse;
    private DistantWarehouse distantWarehouse;
    private GeneralTask onetaskToStart;
    private long waitingTime = 0;
    private VBox vBox1;
    private VBox vBox;

    public ResourceCalculator(Controller controller, LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse,
                              GeneralTask onetaskToStart, VBox vBox1, VBox vBox) {
        this.controller = controller;
        this.localWarehouse = localWarehouse;
        this.distantWarehouse = distantWarehouse;
        this.onetaskToStart = onetaskToStart;
        this.vBox1 = vBox1;
        this.vBox = vBox;
    }


    public void setOnetaskToStart(GeneralTask onetaskToStart) {
        this.onetaskToStart = onetaskToStart;
    }

    @Override
    public synchronized GeneralTask call() throws Exception {
//        onetaskToStart.setIndex(index);
        if (!vBox1.getChildren().isEmpty()) {
            Thread.sleep(readyMaterialsForOneTask(this.onetaskToStart, this.localWarehouse, this.distantWarehouse));
            Platform.runLater(() -> vBox1.getChildren().remove(0));
//            for (Node t : controller.vBox2.getChildren()) {
//                if (((Text) t).getText().contains(String.valueOf(onetaskToStart.getIndex()))) {
//                    Platform.runLater(() -> controller.vBox2.getChildren().remove(t));
//                }
//            }
        } else {
            Platform.runLater(() -> vBox1.getChildren().add(new Text(onetaskToStart.getName())));
            Thread.sleep(readyMaterialsForOneTask(this.onetaskToStart, this.localWarehouse, this.distantWarehouse));
            Platform.runLater(() -> vBox1.getChildren().remove(0));
        }
        ;
//        Platform.runLater(() -> controller.vBox3.getChildren().add(new Text(onetaskToStart.getName() + " " +
//                onetaskToStart.getIndex())));
        Platform.runLater(() -> vBox.getChildren().add(new Text(onetaskToStart.getName())));
//        readyMaterialsForOneTask(this.onetaskToStart, this.localWarehouse, this.distantWarehouse);
        System.out.println("********* All Materials Are Ready*********");
        System.out.println("********* Moving task to In Progress*********");
        return onetaskToStart;
    }

    private synchronized long readyMaterialsForOneTask(GeneralTask oneGeneralTask, LocalWarehouse localWarehouse,
                                                       DistantWarehouse distantWarehouse) {

        Iterator hmIterator = oneGeneralTask.getMaterialsRequired().entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry<AbsMaterial, Integer> mapElement = (Map.Entry) hmIterator.next();
            System.out.println(this.getClass().getSimpleName() + " wants " + mapElement.getKey().getName() + " in" +
                    " quantity " + mapElement.getValue() + " for task " + oneGeneralTask.getName() + " ");
            Integer providedQuantity = localWarehouse.provideMultipleMaterials(mapElement.getKey(),
                    mapElement.getValue());
            if (providedQuantity < mapElement.getValue()) {
                System.out.println("Local warehouse is out of stock for material " + mapElement.getKey().getName());
                System.out.println("Contacting distant warehouse to get required items");
                distantWarehouse.setAbsMaterial(mapElement.getKey());
                distantWarehouse.setNumberOfItems(mapElement.getValue() - providedQuantity);
                distantWarehouse.provideMultipleMaterials(mapElement.getKey(),
                        mapElement.getValue() - providedQuantity);
                waitingTime += (long) DelayUtil.getRandomDoubleBetweenRange(2000, 2500);
                System.out.println("*******Total Waiting time: " + waitingTime + " miliseconds.********");
            }
        }
        return waitingTime;
    }
}
