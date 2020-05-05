package sample.task;

import sample.material.AbsMaterial;

import java.util.Map;

public class GeneralTask implements Task {
    private String name;
    private TaskProduct typeOfTasksProduct;
    private Map<AbsMaterial, Integer> materialsRequired;
    private int quantityToProduce;

    public GeneralTask(String name, TaskProduct typeOfTasksProduct, int quantityToProduce) {
        this.name = name;
        this.typeOfTasksProduct = typeOfTasksProduct;
        this.quantityToProduce = quantityToProduce;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskProduct getTypeOfTasksProduct() {
        return typeOfTasksProduct;
    }

    public void setTypeOfTasksProduct(TaskProduct typeOfTasksProduct) {
        this.typeOfTasksProduct = typeOfTasksProduct;
    }

    public int getQuantityToProduce() {
        return quantityToProduce;
    }

    public void setQuantityToProduce(int quantityToProduce) {
        this.quantityToProduce = quantityToProduce;
    }

    public Map<AbsMaterial, Integer> getMaterialsRequired() {
        return materialsRequired;
    }

    public void setMaterialsRequired(Map<AbsMaterial, Integer> materialsRequired) {
        this.materialsRequired = materialsRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralTask that = (GeneralTask) o;

        if (quantityToProduce != that.quantityToProduce) return false;
        if (!name.equals(that.name)) return false;
        return typeOfTasksProduct.equals(that.typeOfTasksProduct);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + typeOfTasksProduct.hashCode();
        result = 31 * result + quantityToProduce;
        return result;
    }

    @Override
    public String toString() {
        return "GeneralTask{" +
                "name='" + name + '\'' +
                ", typeOfTasksProduct=" + typeOfTasksProduct +
                ", quantityToProduce=" + quantityToProduce +
                '}';
    }
}
