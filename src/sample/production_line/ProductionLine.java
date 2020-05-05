package sample.production_line;

import sample.Controller;
import sample.task.GeneralTask;
import sample.task.MakeBread;
import sample.task.TaskProduct;
import sample.warehouse.Warehouse;

import java.util.List;
import java.util.Queue;

public interface ProductionLine {

    List<TaskProduct> processTask(GeneralTask generalTaskToStart);

    List<TaskProduct> processMultipleMakeBreadTasks(List<MakeBread> makeBreadTasksToStart, Controller controller,
                                                    Warehouse warehouse);



}
