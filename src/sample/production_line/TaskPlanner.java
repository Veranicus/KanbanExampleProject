package sample.production_line;

import javafx.application.Platform;
import javafx.scene.text.Text;
import sample.Controller;
import sample.delay.DelayUtil;

import java.util.List;

//Class for scheduling and planning of task used by production lines
public class TaskPlanner {

    volatile Boolean inProcess = false;

    public synchronized void finishMultipleTasks(List<Text> listOfTaskForFinish, Controller controller) {
        System.out.println(inProcess);
//        while (inProcess = true) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        System.out.println(inProcess);
        inProcess = true;
        new Thread(() -> {
            for (Text text : listOfTaskForFinish) {
                try {
                    long productionTime = (long) DelayUtil.getRandomDoubleBetweenRange(2000, 2500);
                    System.out.println(this.getClass().getSimpleName() + " Production time of " + text.getText()
                            + " is " + productionTime);
                    Thread.sleep(productionTime);
                    Platform.runLater(() -> controller.vBox3.getChildren().add(text));// Update on JavaFX Application Thread
                    System.out.println("Finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        inProcess = false;
    }
}

