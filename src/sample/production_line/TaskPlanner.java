package sample.production_line;

import sample.Controller;
import sample.delay.DelayUtil;
import sample.task.GeneralTask;

import java.util.concurrent.Callable;

//Class for scheduling and planning of task, used by production lines
public class TaskPlanner implements Callable<GeneralTask> {

    private GeneralTask oneTaskToFinish;
    private Controller controller;

    public TaskPlanner(GeneralTask oneTaskToFinish, Controller controller) {
        this.oneTaskToFinish = oneTaskToFinish;
        this.controller = controller;
    }

    public TaskPlanner() {
    }

    @Override
    public GeneralTask call() throws Exception {
        Thread.sleep(finishMultipleTasks(oneTaskToFinish));
        return oneTaskToFinish;
    }

    public long finishMultipleTasks(GeneralTask taskToFinish) {
        long productionTime = (long) DelayUtil.getRandomDoubleBetweenRange(taskToFinish.getMinProductionInterval(),
                taskToFinish.getMaxProducitonInterval());
        System.out.println("******** " + this.getClass().getSimpleName() + " Production time of " + taskToFinish.getName()
                + " is " + productionTime + "ms **********");
        return productionTime;
    }
}

