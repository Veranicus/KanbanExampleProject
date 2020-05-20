package sample.task;

import sample.material.AbsMaterial;
import sample.material.Beef;
import sample.material.Bun;
import sample.material.Cheese;
import sample.task.task_product.TaskProduct;

import java.util.HashMap;
import java.util.Map;

public class MakeHamburger extends GeneralTask {

    public MakeHamburger(String name, TaskProduct typeOfTasksProduct, int quantityToProduce) {
        super(name, typeOfTasksProduct, quantityToProduce);
        Map<AbsMaterial, Integer> ingredients = new HashMap<>();
        ingredients.put(new Bun(), 2 * quantityToProduce);
        ingredients.put(new Beef(), 1 * quantityToProduce);
        ingredients.put(new Cheese(), 3 * quantityToProduce);
        this.setMaterialsRequired(ingredients);
        this.setMinProductionInterval(312);
        this.setMaxProducitonInterval(568);
    }
}