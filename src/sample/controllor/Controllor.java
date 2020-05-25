package sample.controllor;

import javafx.application.Platform;
import javafx.scene.text.Text;
import sample.Controller;
import sample.delay.DelayUtil;
import sample.production_line.ProductionLineC;
import sample.task.GeneralTask;
import sample.task.task_product.TaskProduct;
import sample.warehouse.DistantWarehouse;
import sample.warehouse.LocalWarehouse;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

public class Controllor implements Callable {
    Queue<GeneralTask> faultyTasks;

    ProductionLineC productionLineC;

    Controller controller;

    LocalWarehouse localWarehouse;

    DistantWarehouse distantWarehouse;

    ExecutorService pool;

    int numberOFTasksToFix;

    Stack<Text> faultyTasksDisplay;

    public void addFaultyTask(GeneralTask generalTask) {
        generalTask.setName(generalTask.getName() + " Fix");
        this.faultyTasks.add(generalTask);
        System.out.println("FAULTY TASK COUNT " + generalTask);
    }

    public void clearFaultyTask() {
        this.faultyTasks = new ConcurrentLinkedQueue<>();
    }


    public int getNumberOFTasksToFix() {
        return numberOFTasksToFix;
    }


    public Controllor(Controller controller,
                      LocalWarehouse localWarehouse, DistantWarehouse distantWarehouse, ExecutorService pool,
                      int numberOFTasksToFix, ProductionLineC productionLineC) {
        this.faultyTasks = new ConcurrentLinkedQueue<>();
        this.controller = controller;
        this.localWarehouse = localWarehouse;
        this.distantWarehouse = distantWarehouse;
        this.pool = pool;
        this.numberOFTasksToFix = numberOFTasksToFix;
        this.productionLineC = productionLineC;
    }

    public void setProductionLineC(ProductionLineC productionLineC) {
        this.productionLineC = productionLineC;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setLocalWarehouse(LocalWarehouse localWarehouse) {
        this.localWarehouse = localWarehouse;
    }

    public void setDistantWarehouse(DistantWarehouse distantWarehouse) {
        this.distantWarehouse = distantWarehouse;
    }

    public void setPool(ExecutorService pool) {
        this.pool = pool;
    }

    public Queue<GeneralTask> getFaultyTasks() {
        return faultyTasks;
    }

    public static boolean controlTask(GeneralTask generalTask) {
        if (DelayUtil.getRandomDoubleBetweenRange(1, 100) <= 5) {
            return false;
        } else return true;
    }

    public void setFaultyTasks(Queue<GeneralTask> faultyTasks) {
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

    public void turnQueueOfTasksIntoTextStack(Queue<GeneralTask> faultyTasks, Stack<Text> faultyText) {
        for (GeneralTask g : faultyTasks) {
            faultyText.add(new Text(g.getName()));
        }
    }


    public void setNumberOFTasksToFix(int numberOFTasksToFix) {
        this.numberOFTasksToFix = numberOFTasksToFix;
    }

    @Override
    public Object call() throws Exception {
        if (!faultyTasks.isEmpty()) {
            turnQueueOfTasksIntoTextStack(this.faultyTasks, faultyTasksDisplay);
            Platform.runLater(() -> controller.vBox211.getChildren().addAll(faultyTasksDisplay));
            productionLineC.setOneTaskQueue(faultyTasks);
            productionLineC.call();
            this.faultyTasks.clear();
        }
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
