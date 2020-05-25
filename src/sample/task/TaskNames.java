package sample.task;

public enum TaskNames {
    TASK1("Make Bread"),
    TASK2("Make Omelette"),
    TASK3("Make Soup"),
    TASK4("Make Pizza"),
    TASK5("Make Hamburger"),
    ;

    public final String label;

    TaskNames(String label) {
        this.label = label;
    }
}
