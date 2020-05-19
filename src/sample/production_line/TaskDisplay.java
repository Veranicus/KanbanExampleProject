package sample.production_line;

import javafx.application.Platform;
import javafx.scene.text.Text;
import sample.Controller;
import sample.task.GeneralTask;

import java.util.concurrent.Callable;

public class TaskDisplay implements Callable {

    GeneralTask taskToShow;
    Controller controller;

    public TaskDisplay(GeneralTask taskToShow, Controller controller) {
        this.taskToShow = taskToShow;
        this.controller = controller;
    }

    @Override
    public Object call() throws Exception {
        Platform.runLater(() -> controller.vBox3.getChildren().add(new Text(taskToShow.getName())));
        return null;
    }
}
