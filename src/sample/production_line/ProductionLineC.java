package sample.production_line;

import sample.Controller;
import sample.task.GeneralTask;
import sample.warehouse.DistantWarehouse;
import sample.warehouse.LocalWarehouse;

import java.util.List;

public class ProductionLineC implements ProductionLine {

    @Override
    public void processMultipleTasks(List<GeneralTask> makeBreadTasksToStart, Controller controller,
                                     LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse) {
    }
}
