package sample.warehouse;

import sample.material.AbsMaterial;
import sample.material.Flour;
import sample.material.Salt;
import sample.material.Water;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Local warehouse which is programmed to have some initial number of materials for the tasks which can be brought
//in instantly
public class LocalWarehouse implements Warehouse {

    Map<AbsMaterial, Integer> localIngredients;

    public Map<AbsMaterial, Integer> getLocalIngredients() {
        return localIngredients;
    }

    public void setLocalIngredients(Map<AbsMaterial, Integer> localIngredients) {
        this.localIngredients = localIngredients;
    }

    public LocalWarehouse() {
        this.localIngredients = new HashMap<>();
        localIngredients.put(new Flour(), 3);
        localIngredients.put(new Salt(), 1);
        localIngredients.put(new Water(), 2);
    }

    @Override
    public Integer provideQuantityOfOneMaterial(AbsMaterial generalMaterial) {
        System.out.println("Providing " + generalMaterial.getName() + " of quantity " +
                localIngredients.get(generalMaterial));
        return localIngredients.get(generalMaterial);
    }

    @Override
    public List<AbsMaterial> provideMultipleMaterials(AbsMaterial generalMaterial, int numberOfItems) {
    return null;
    }
}
