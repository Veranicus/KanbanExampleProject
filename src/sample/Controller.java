package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.controllor.Controllor;
import sample.production_line.ProductionLineA;
import sample.production_line.ProductionLineB;
import sample.production_line.ProductionLineC;
import sample.production_line.TaskPlanner;
import sample.task.*;
import sample.task.task_product.*;
import sample.warehouse.DistantWarehouse;
import sample.warehouse.LocalWarehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller {
    @FXML
    public ListView listView1;
    @FXML
    public CheckBox checkBox1;
    @FXML
    public CheckBox checkBox2;
    @FXML
    public CheckBox checkBox3;
    @FXML
    public CheckBox checkBox4;
    @FXML
    public CheckBox checkBox5;
    public VBox vBox31;
    public VBox vBox311;
    public VBox vBox21;
    public VBox vBox211;

    private int task1Index = 1;
    private int task2Index = 1;
    private int task3Index = 1;
    private int task4Index = 1;
    private int task5Index = 1;

    @FXML
    public VBox vBox1;
    public Button button1remove;
    public Button button1add;
    public Button button2remove;
    public Button button2add;
    public Button button3remove;
    public Button button3add;
    public Button button4remove;
    public Button button4add;
    public Button button5remove;
    public Button button5add;
    public VBox vBox2;
    public VBox vBoxStart;
    @FXML
    public VBox vBox3;
    public ScrollPane scrollPane3;
    @FXML
    public Text textShow1;
    public VBox vBox4;

    Stack<Text> task1Display = new Stack<>();
    Stack<Text> task2Display = new Stack<>();
    Stack<Text> task3Display = new Stack<>();
    Stack<Text> task4Display = new Stack<>();
    Stack<Text> task5Display = new Stack<>();
    Stack<Text> faultyTasksDisplay = new Stack<>();

    Queue<GeneralTask> tasks = new ConcurrentLinkedQueue<>();
    Queue<GeneralTask> tasks1 = new ConcurrentLinkedQueue<>();
    Queue<GeneralTask> tasks2 = new ConcurrentLinkedQueue<>();
    Queue<GeneralTask> faultyTasks = new ConcurrentLinkedQueue<>();


    List<GeneralTask> makeBreadToDoList = new ArrayList<>();
    List<GeneralTask> makeOmeletteToDoList = new ArrayList<>();
    List<GeneralTask> makeChickenSoupToDoList = new ArrayList<>();
    List<GeneralTask> makePizzaToDoList = new ArrayList<>();
    List<GeneralTask> makeHamburgerToDoList = new ArrayList<>();
    List<GeneralTask> faultyTasks2 = new ArrayList<>();

    public Stack<Text> getFaultyTasksDisplay() {
        return faultyTasksDisplay;
    }

    public LocalWarehouse getLocalWarehouse() {
        return localWarehouse;
    }

    public DistantWarehouse getDistantWarehouse() {
        return distantWarehouse;
    }

    public void addFaultyTask(GeneralTask generalTask) {
        controllor.addFaultyTaskCount();
        generalTask.setName(generalTask.getName() + "Fix");
        this.faultyTasks.add(generalTask);
        System.out.println("FAULTY TASK COUNT " + controllor.getNumberOFTasksToFix());
    }

    public void clearFaultyTask() {
        this.faultyTasks = new ConcurrentLinkedQueue<>();
    }

    int n = 10;
    ExecutorService pool = Executors.newFixedThreadPool(3);
//    ExecutorService pool2 = Executors.newFixedThreadPool(20);
//    ExecutorService poolFix = Executors.newWorkStealingPool();

    LocalWarehouse localWarehouse = new LocalWarehouse();
    DistantWarehouse distantWarehouse = new DistantWarehouse();
    TaskPlanner taskPlanner = new TaskPlanner();
    Controllor controllor = new Controllor(this, localWarehouse, distantWarehouse, pool, 5,
            new ProductionLineC());
    //    ProductionLineB productionLineB = new ProductionLineB();
//    ProductionLineB productionLine2 = new ProductionLineB(tasks, this,
//            localWarehouse, distantWarehouse);

    @FXML
    Button button1 = new Button();

    @FXML
    public synchronized void buttonStartClicked(Event e) {
        System.out.println("Button Clicked");
        vBoxStart.setDisable(true);
//        for (int i = 0; i < task1Display.size(); i++) {
//            vBox2.getChildren().add(new Text(task1Display.get(i).getText() + " " + i));
//        }
        vBox2.getChildren().addAll(task1Display);
        vBox2.getChildren().addAll(task2Display);
        vBox21.getChildren().addAll(task3Display);
        vBox21.getChildren().addAll(task4Display);
        vBox211.getChildren().addAll(task5Display);
        System.out.println(task2Display);

        Thread taskThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                turnTextStackIntoQueOfTasks(task1Display, new Bread());
                turnTextStackIntoQueOfTasks(task2Display, new Omelette());
                turnTextStackIntoQueOfTasks(task3Display, new ChickenSoup());
                turnTextStackIntoQueOfTasks(task4Display, new Pizza());
                turnTextStackIntoQueOfTasks(task5Display, new Hamburger());

//                ProductionLineB productionLine2 = new ProductionLineB(this,
//                        localWarehouse, distantWarehouse);

//                tasks.add(new MakeHamburger("test", new Hamburger(), 1,0));
//                tasks.add(new MakePizza("test", new Hamburger(), 1,1));
//                tasks.add(new MakePizza("test", new Hamburger(), 1,2));

                ProductionLineA productionLine1 = new ProductionLineA(tasks, this,
                        localWarehouse, distantWarehouse);
                ProductionLineB productionLine2 = new ProductionLineB(tasks1, this,
                        localWarehouse, distantWarehouse);
                ProductionLineC productionLine3 = new ProductionLineC(tasks2, this,
                        localWarehouse, distantWarehouse);

//                    GeneralTask result1 = pool.submit(tasks.poll()).get();
//                    GeneralTask result2 = pool.submit(tasks.poll()).get();

//                        productionLine2.setOneTask(pool.submit(tasks.poll()).get());
                if (!tasks.isEmpty()) {
                    pool.submit(productionLine1);
                }
                if (!tasks1.isEmpty()) {
                    pool.submit(productionLine2);
                }
                if (!tasks2.isEmpty()) {
                    pool.submit(productionLine3);
                }
//                pool.submit(productionLine2);
//                pool.submit(productionLine3);


                Thread.sleep(500);


//                while (controllor.getNumberOFTasksToFix() != 0) {
//                    System.out.println(controllor.getNumberOFTasksToFix());
//                    addAllTasksToVbox2(faultyTasks);
////                     controllor.setNumberOFTasksToFix(faultyTasks.size());
//                    faultyTasks2.addAll(faultyTasks);
//                    beginWorkingOnGeneralTask(faultyTasks2, new ProductionLineC());
//                    faultyTasks2.clear();
//                    Platform.runLater(() -> vBox2.getChildren().clear());
//                }
                pool.shutdown();
                try {
                    pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                    while (!faultyTasks.isEmpty()) {
                        turnQueueOfTasksIntoTextStack(faultyTasks, faultyTasksDisplay);
                        Platform.runLater(() -> vBox211.getChildren().addAll(faultyTasksDisplay));
                        productionLine3.setOneTaskQueue(faultyTasks);
                        productionLine3.call();
                    }
//                    Controllor controllor = new Controllor(this,localWarehouse,distantWarehouse,
//                            pool,4, productionLine3);
//                    controllor.call();
                } catch (Exception g) {
                }
            } catch (InterruptedException f) {
                f.printStackTrace();
            } finally {
            }
        });
        taskThread.start();

    }

    public void turnQueueOfTasksIntoTextStack(Queue<GeneralTask> faultyTasks, Stack<Text> faultyText) {
        for (GeneralTask g : faultyTasks) {
            faultyText.add(new Text(g.getName()));
        }
    }

    public void turnTextStackIntoQueOfTasks(Stack<Text> currentTexts,
                                            TaskProduct taskProduct) {
        if (!currentTexts.isEmpty()) {
            if (taskProduct.getNameOfTaskProduct().equalsIgnoreCase("bread")) {
                for (int i = 0; i < currentTexts.size(); i++) {
                    tasks.add(new MakeBread(currentTexts.get(i).getText(),
                            taskProduct, 1, i));
//                    System.out.println(tasks.get(i));
                }
            } else if (taskProduct.getNameOfTaskProduct().equalsIgnoreCase("omelette")) {
                for (int i = 0; i < currentTexts.size(); i++) {
                    tasks.add(new MakeOmellete(currentTexts.get(i).getText(),
                            taskProduct, 1, i));
//                    System.out.println(tas.get(i));
                }
            } else if (taskProduct.getNameOfTaskProduct().equalsIgnoreCase("chickensoup")) {
                for (int i = 0; i < currentTexts.size(); i++) {
                    tasks1.add(new MakeChickenSoup(currentTexts.get(i).getText(),
                            taskProduct, 1, i));
//                    System.out.println(makeChickenSoupToDoList.get(i));
                }
            } else if (taskProduct.getNameOfTaskProduct().equalsIgnoreCase("pizza")) {
                for (int i = 0; i < currentTexts.size(); i++) {
                    tasks1.add(new MakePizza(currentTexts.get(i).getText(),
                            taskProduct, 1, i));
//                    System.out.println(makePizzaToDoList.get(i));
                }
            } else if (taskProduct.getNameOfTaskProduct().equalsIgnoreCase("hamburger")) {
                for (int i = 0; i < currentTexts.size(); i++) {
                    tasks2.add(new MakeHamburger(currentTexts.get(i).getText(),
                            taskProduct, 1, i));
//                    System.out.println(makeHamburgerToDoList.get(i));
                }
            }
        }
    }


//    public void beginWorkingOnGeneralTask(List<GeneralTask> tasks, ProductionLine productionLine) {
//        if (!tasks.isEmpty()) {
//            System.out.println("TOTAL FAULT TO REPAIR" + controllor.getNumberOFTasksToFix());
//            controllor.nullFaultyTaskCount();
//            faultyTasks.clear();
////            productionLine.processMultipleTasks(tasks, this, localWarehouse, distantWarehouse, pool);
//        }
//    }

    public synchronized void addAllTasksToVbox2(List<GeneralTask> tasks) {
        if (!tasks.isEmpty()) {
            for (GeneralTask g : tasks)
                Platform.runLater((() -> vBox2.getChildren().add(new Text(g.getName()))));
        }
    }

    @FXML
    public void initialize() {
//        button1.setDisable(true);
    }


    public Controllor getControllor() {
        return controllor;
    }

    public void buttonClickedRemove1(ActionEvent actionEvent) {
        if (!task1Display.isEmpty()) {
            if (task1Index > 0) {
                task1Index--;
            }
            vBox1.getChildren().remove(task1Display.peek());
            task1Display.pop();
        }
    }

    public void buttonClickedAdd1(ActionEvent actionEvent) {
        Text text = new Text(TaskNames.TASK1.label + " " + task1Index);
        task1Index++;
        task1Display.push(text);
        vBox1.getChildren().add(task1Display.peek());
    }

    public void buttonClickedRemove2(ActionEvent actionEvent) {
        if (!task2Display.isEmpty()) {
            if (task2Index > 0) {
                task2Index--;
            }
            vBox1.getChildren().remove(task2Display.peek());
            task2Display.pop();
        }
    }

    public void buttonClickedAdd2(ActionEvent actionEvent) {
        Text text = new Text(TaskNames.TASK2.label + " " + task2Index);
        task2Index++;
        task2Display.push(text);
        vBox1.getChildren().add(task2Display.peek());
    }

    public void buttonClickedRemove3(ActionEvent actionEvent) {
        if (!task3Display.isEmpty()) {
            if (task3Index > 0) {
                task3Index--;
            }
            vBox1.getChildren().remove(task3Display.peek());
            task3Display.pop();
        }
    }

    public void buttonClickedAdd3(ActionEvent actionEvent) {
        Text text = new Text(TaskNames.TASK3.label + " " + task3Index);
        task3Index++;
        task3Display.push(text);
        vBox1.getChildren().add(task3Display.peek());
    }

    public void buttonClickedRemove4(ActionEvent actionEvent) {
        if (!task4Display.isEmpty()) {
            if (task4Index > 0) {
                task4Index--;
            }
            vBox1.getChildren().remove(task4Display.peek());
            task4Display.pop();
        }
    }

    public void buttonClickedAdd4(ActionEvent actionEvent) {
        Text text = new Text(TaskNames.TASK4.label + " " + task4Index);
        task4Index++;
        task4Display.push(text);
        vBox1.getChildren().add(task4Display.peek());
    }

    public void buttonClickedRemove5(ActionEvent actionEvent) {
        if (!task5Display.isEmpty()) {
            if (task5Index > 0) {
                task5Index--;
            }
            vBox1.getChildren().remove(task5Display.peek());
            task5Display.pop();
        }
    }

    public void buttonClickedAdd5(ActionEvent actionEvent) {
        Text text = new Text(TaskNames.TASK5.label + " " + task5Index);
        task5Index++;
        task5Display.push(text);
        vBox1.getChildren().add(task5Display.peek());
    }

    public void resetApplication(ActionEvent actionEvent) {
        resetApplication(actionEvent);
    }

    public Stack<Text> getTask1Display() {
        return task1Display;
    }

    public Stack<Text> getTask2Display() {
        return task2Display;
    }

    public Stack<Text> getTask3Display() {
        return task3Display;
    }

    public Stack<Text> getTask4Display() {
        return task4Display;
    }

    public Stack<Text> getTask5Display() {
        return task5Display;
    }
}
