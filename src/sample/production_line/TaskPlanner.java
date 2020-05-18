package sample.production_line;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.text.Text;
import sample.Controller;
import sample.delay.DelayUtil;

import java.util.List;

//Class for scheduling and planning of task, used by production lines
public class TaskPlanner extends Task {

    private List<Text> listOfTaskForFinish;
    private Controller controller;

    public TaskPlanner(List<Text> listOfTaskForFinish, Controller controller) {
        this.listOfTaskForFinish = listOfTaskForFinish;
        this.controller = controller;
    }

    public TaskPlanner() {
    }

    @Override
    protected Object call() throws Exception {
        finishMultipleTasks(this.listOfTaskForFinish, this.controller);
        return null;
    }


    public synchronized void finishMultipleTasks(List<Text> listOfTaskForFinish, Controller controller) {
        try {

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
            }).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

