package sample.production_line;

import javafx.scene.text.Text;
import sample.Controller;
import sample.task.GeneralTask;
import sample.warehouse.DistantWarehouse;
import sample.warehouse.LocalWarehouse;

import java.util.List;

public class ProductionLineC implements ProductionLine {

    @Override
    public List<Text> processMultipleTasks(List<GeneralTask> makeBreadTasksToStart, Controller controller,
                                           LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse, TaskPlanner taskPlanner) {
        return null;
    }
}
