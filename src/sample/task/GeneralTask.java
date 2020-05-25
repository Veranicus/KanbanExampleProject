package sample.task;

import sample.material.AbsMaterial;
import sample.task.task_product.TaskProduct;

import java.util.Map;
import java.util.concurrent.Callable;

public class GeneralTask implements Task, Callable<GeneralTask> {
    private String name;
    private TaskProduct typeOfTasksProduct;
    private Map<AbsMaterial, Integer> materialsRequired;
    private int quantityToProduce;
    private double minProductionInterval;
    private double maxProducitonInterval;
    private int index;

    public GeneralTask(String name, TaskProduct typeOfTasksProduct, int quantityToProduce, int index) {
        this.name = name;
        this.typeOfTasksProduct = typeOfTasksProduct;
        this.quantityToProduce = quantityToProduce;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public double getMinProductionInterval() {
        return minProductionInterval;
    }

    public void setMinProductionInterval(double minProductionInterval) {
        this.minProductionInterval = minProductionInterval;
    }

    public double getMaxProducitonInterval() {
        return maxProducitonInterval;
    }

    public void setMaxProducitonInterval(double maxProducitonInterval) {
        this.maxProducitonInterval = maxProducitonInterval;
    }

    public void setIndex(int index) {
        this.index = index;
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

        return index == that.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        return "GeneralTask{" +
                "name='" + name + '\'' +
                ", typeOfTasksProduct=" + typeOfTasksProduct +
                ", quantityToProduce=" + quantityToProduce +
                '}';
    }

    @Override
    public GeneralTask call() throws Exception {
        return this;
    }
}
