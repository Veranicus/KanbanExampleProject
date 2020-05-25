package sample.production_line;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.Controller;
import sample.controllor.Controllor;
import sample.task.GeneralTask;

import java.util.concurrent.Callable;

public class TaskDisplay implements Callable<Boolean> {

    GeneralTask taskToShow;
    Controller controller;
    VBox vbox;


    public TaskDisplay(GeneralTask taskToShow, Controller controller, VBox vBox) {
        this.taskToShow = taskToShow;
        this.controller = controller;
        this.vbox = vBox;
    }

    @Override
    public Boolean call() throws Exception {
        System.out.println("************** Displaying " + taskToShow.getName() + " *****************");
        if (!vbox.getChildren().isEmpty()) {
            Platform.runLater(() -> vbox.getChildren().remove(0));
        }
        if (!Controllor.controlTask(taskToShow)) {
            System.out.println("CONTROLLER FOUND AN ERROR, PUTTING THIS TASK BACK TO PRODUCTION");
            controller.addFaultyTask(taskToShow);
            return false;
        } else {
            Platform.runLater(() -> controller.vBox4.getChildren().add(new Text(taskToShow.getName())));
            return true;
        }
    }
}
