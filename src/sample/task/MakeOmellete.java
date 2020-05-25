package sample.task;

import sample.material.AbsMaterial;
import sample.material.Egg;
import sample.material.Onion;
import sample.material.Salt;
import sample.task.task_product.TaskProduct;

import java.util.HashMap;
import java.util.Map;

public class MakeOmellete extends GeneralTask {

    public MakeOmellete(String name, TaskProduct typeOfTasksProduct, int quantityToProduce, int index) {
        super(name, typeOfTasksProduct, quantityToProduce, index);
        Map<AbsMaterial, Integer> ingredients = new HashMap<>();
        ingredients.put(new Salt(), 1 * quantityToProduce);
        ingredients.put(new Egg(), 3 * quantityToProduce);
        ingredients.put(new Onion(), 1 * quantityToProduce);
        this.setMaterialsRequired(ingredients);
        this.setMinProductionInterval(800);
        this.setMaxProducitonInterval(1950);
    }

}
