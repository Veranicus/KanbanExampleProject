package sample.task;

import sample.material.AbsMaterial;
import sample.material.Flour;
import sample.material.Salt;
import sample.material.Water;
import sample.task.task_product.TaskProduct;

import java.util.HashMap;
import java.util.Map;

public class MakeBread extends GeneralTask implements Task {

    public MakeBread(String name, TaskProduct typeOfTasksProduct, int quantityToProduce) {
        super(name, typeOfTasksProduct, quantityToProduce);
        Map<AbsMaterial, Integer> ingredients = new HashMap<>();
        ingredients.put(new Salt(), 1 * quantityToProduce);
        ingredients.put(new Flour(), 3 * quantityToProduce);
        ingredients.put(new Water(), 2 * quantityToProduce);
        this.setMaterialsRequired(ingredients);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
