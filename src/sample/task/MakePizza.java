package sample.task;

import sample.material.AbsMaterial;
import sample.material.Cheese;
import sample.material.Ham;
import sample.material.Tomato;
import sample.task.task_product.TaskProduct;

import java.util.HashMap;
import java.util.Map;

public class MakePizza extends GeneralTask {

    public MakePizza(String name, TaskProduct typeOfTasksProduct, int quantityToProduce) {
        super(name, typeOfTasksProduct, quantityToProduce);
        Map<AbsMaterial, Integer> ingredients = new HashMap<>();
        ingredients.put(new Cheese(), 2 * quantityToProduce);
        ingredients.put(new Ham(), 1 * quantityToProduce);
        ingredients.put(new Tomato(), 4 * quantityToProduce);
        this.setMaterialsRequired(ingredients);
        this.setMinProductionInterval(230);
        this.setMaxProducitonInterval(731);
    }
}
