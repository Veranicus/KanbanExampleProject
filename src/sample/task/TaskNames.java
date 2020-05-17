package sample.task;

public enum TaskNames {
    TASK1("Make Bread"),
    TASK2("Make Omelette"),
    TASK3("Task3"),
    TASK4("Task4"),
    TASK5("Task5"),
    ;

    public final String label;

    TaskNames(String label) {
        this.label = label;
    }
}
