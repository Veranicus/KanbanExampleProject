package sample.task.task_product;

public class Hamburger implements TaskProduct {

    private final static String name = "Hamburger";

    @Override
    public String getNameOfTaskProduct() {
        return name;
    }
}
