package sample.production_line;

import javafx.scene.text.Text;
import sample.Controller;
import sample.delay.DelayUtil;
import sample.task.GeneralTask;
import sample.task.task_product.TaskProduct;
import sample.warehouse.DistantWarehouse;
import sample.warehouse.LocalWarehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class Controllor implements Callable {
    List<GeneralTask> faultyTasks;

    ProductionLine productionLine;

    Controller controller;

    LocalWarehouse localWarehouse;

    DistantWarehouse distantWarehouse;

    ExecutorService pool;

    int numberOFTasksToFix;

    public void addFaultyTask(GeneralTask generalTask) {
        generalTask.setName(generalTask.getName() + "Fix");
        this.faultyTasks.add(generalTask);
    }

    public void clearFaultyTask() {
        this.faultyTasks = new ArrayList<>();
    }


    public int getNumberOFTasksToFix() {
        return numberOFTasksToFix;
    }

    public Controllor(ProductionLine productionLine,
                      Controller controller, LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse,
                      ExecutorService pool) {
        this.faultyTasks = new ArrayList<>();
        this.productionLine = productionLine;
        this.controller = controller;
        this.localWarehouse = localWarehouse;
        this.distantWarehouse = distantWarehouse;
        this.pool = pool;
    }

    public Controllor(List<GeneralTask> faultyTasks, Controller controller,
                      LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse, ExecutorService pool, int numberOFTasksToFix) {
        this.faultyTasks = faultyTasks;
        this.controller = controller;
        this.localWarehouse = localWarehouse;
        this.distantWarehouse = distantWarehouse;
        this.pool = pool;
        this.numberOFTasksToFix = numberOFTasksToFix;
    }

    public List<GeneralTask> getFaultyTasks() {
        return faultyTasks;
    }

    public static boolean controlTask(GeneralTask generalTask) {
        if (DelayUtil.getRandomDoubleBetweenRange(1, 100) <= 5) {
            return false;
        } else return true;
    }

    public void setFaultyTasks(List<GeneralTask> faultyTasks) {
        this.faultyTasks = faultyTasks;
    }

    public void turnGeneralTaskToRespectiveTextStack(TaskProduct taskProduct) {
        if (taskProduct.getNameOfTaskProduct().equalsIgnoreCase("bread")) {
            controller.getTask1Display().add(new Text(taskProduct.getNameOfTaskProduct()));
        } else if (taskProduct.getNameOfTaskProduct().equalsIgnoreCase("omelette")) {
            controller.getTask1Display().add(new Text(taskProduct.getNameOfTaskProduct()));
        } else if (taskProduct.getNameOfTaskProduct().equalsIgnoreCase("chickensoup")) {
            controller.getTask1Display().add(new Text(taskProduct.getNameOfTaskProduct()));
        } else if (taskProduct.getNameOfTaskProduct().equalsIgnoreCase("pizza")) {
            controller.getTask1Display().add(new Text(taskProduct.getNameOfTaskProduct()));
        } else if (taskProduct.getNameOfTaskProduct().equalsIgnoreCase("hamburger")) {
            controller.getTask1Display().add(new Text(taskProduct.getNameOfTaskProduct()));
        }
    }

    public void addFaultyTaskCount() {
        this.numberOFTasksToFix++;
    }

    public void nullFaultyTaskCount() {
        this.numberOFTasksToFix = 0;
    }


    public void setNumberOFTasksToFix(int numberOFTasksToFix) {
        this.numberOFTasksToFix = numberOFTasksToFix;
    }

    @Override
    public Object call() throws Exception {
//        Thread.sleep(1000);
//        Instant start = Instant.now();
//        if (!faultyTasks.isEmpty()) {
//            try {
//                for (GeneralTask g : faultyTasks) {
//                    Platform.runLater(() -> controller.vBox2.getChildren().add(new Text(g.getName())));
//                    Thread.sleep(500);
//                    GeneralTask generalTaskToCalculate = pool.submit(new ResourceCalculator(faultyTasks, controller,
//                            localWarehouse, distantWarehouse, g)).get();
//                    GeneralTask generalTaskToShow = pool.submit(new TaskPlanner(generalTaskToCalculate, controller)).get();
//                    pool.submit(new TaskDisplay(generalTaskToShow, controller));
//                }
//                Instant end = Instant.now();
//                System.out.println("***** Total Time to proces" + faultyTasks.get(0).getName() + " Group of tasks is " +
//                        Duration.between(start, end).toMillis() + "********");
//            } catch (Exception E) {
//                E.printStackTrace();
//            }
//        }
        return null;
    }
}
