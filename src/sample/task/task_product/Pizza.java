package sample.task.task_product;

public class Pizza implements TaskProduct {

    private final static String name = "Omelette";

    @Override
    public String getNameOfTaskProduct() {
        return name;
    }
}
