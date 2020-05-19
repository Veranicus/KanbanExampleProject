package sample.task.task_product;

public class ChickenSoup implements TaskProduct {

    private final static String name = "ChickenSoup";

    @Override
    public String getNameOfTaskProduct() {
        return name;
    }
}
