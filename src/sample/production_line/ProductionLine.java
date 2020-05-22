package sample.production_line;

import sample.Controller;
import sample.task.GeneralTask;
import sample.warehouse.DistantWarehouse;
import sample.warehouse.LocalWarehouse;

import java.util.Queue;

public interface ProductionLine {

    void processOneTask(Queue<GeneralTask> makeBreadTasksToStart, Controller controller,
                        LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse);


}
