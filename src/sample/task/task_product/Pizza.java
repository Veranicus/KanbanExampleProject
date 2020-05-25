package sample.task.task_product;

public class Pizza implements TaskProduct {

    private final static String name = "Pizza";

    @Override
    public String getNameOfTaskProduct() {
        return name;
    }
}
