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
        System.out.println("************** Displaying " + taskToShow.getName() + " *****************");
        Platform.runLater(() -> controller.vBox3.getChildren().remove(0));
        if (!Controllor.controlTask(taskToShow)) {
            System.out.println("CONTROLLER FOUND AN ERROR, PUTTING THIS TASK BACK TO PRODUCTION");
            controller.addFaultyTask(taskToShow);
//            controller.getControllor().addFaultyTaskCount();
//            controller.getControllor().addFaultyTask(taskToShow);
//            controller.getControllor().turnGeneralTaskToRespectiveTextStack(taskToShow.getTypeOfTasksProduct());
            return taskToShow;
        } else {
            Platform.runLater(() -> controller.vBox4.getChildren().add(new Text(taskToShow.getName())));
            return null;
        }
    }
}
