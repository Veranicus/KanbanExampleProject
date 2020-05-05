package sample.task;
//Class representing product of task Make Bread
public class Bread implements TaskProduct {

    private final static String name = "Bread";

    @Override
    public String getNameOfTaskProduct() {
        return name;
    }
}
