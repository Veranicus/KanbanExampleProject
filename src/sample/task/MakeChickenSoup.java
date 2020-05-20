package sample.task;

import sample.material.AbsMaterial;
import sample.material.Carrot;
import sample.material.ChickenWing;
import sample.material.Water;
import sample.task.task_product.TaskProduct;

import java.util.HashMap;
import java.util.Map;

public class MakeChickenSoup extends GeneralTask {

    public MakeChickenSoup(String name, TaskProduct typeOfTasksProduct, int quantityToProduce) {
        super(name, typeOfTasksProduct, quantityToProduce);
        Map<AbsMaterial, Integer> ingredients = new HashMap<>();
        ingredients.put(new Carrot(), 1 * quantityToProduce);
        ingredients.put(new ChickenWing(), 3 * quantityToProduce);
        ingredients.put(new Water(), 3 * quantityToProduce);
        this.setMaterialsRequired(ingredients);
        this.setMinProductionInterval(250);
        this.setMaxProducitonInterval(425);
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
