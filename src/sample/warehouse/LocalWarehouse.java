package sample.warehouse;

import sample.material.*;

import java.util.HashMap;
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
        localIngredients.put(new Water(), 5);
        localIngredients.put(new Egg(), 3);
        localIngredients.put(new Onion(), 1);
        localIngredients.put(new ChickenWing(), 3);
        localIngredients.put(new Carrot(), 3);
        localIngredients.put(new Cheese(), 3);
        localIngredients.put(new Ham(), 2);
        localIngredients.put(new Tomato(), 4);
        localIngredients.put(new Bun(), 2);
        localIngredients.put(new Beef(), 1);
        localIngredients.put(new Cheese(), 3);
    }

    @Override
    public Integer provideQuantityOfOneMaterial(AbsMaterial generalMaterial) {
        Integer returnQuantity = 0;
        System.out.println("Providing " + generalMaterial.getName() + " of quantity " +
                localIngredients.get(generalMaterial));
        returnQuantity = localIngredients.get(generalMaterial);
        if (localIngredients.get(generalMaterial) > 0) {
            localIngredients.replace(generalMaterial, localIngredients.get(generalMaterial) - returnQuantity);
            if (localIngredients.get(generalMaterial) < 0) {
                returnQuantity = 0;
                System.out.println("We have only ");
                return returnQuantity;
            } else {
                return returnQuantity;
            }
        }
        return returnQuantity;
    }

    @Override
    public synchronized Integer provideMultipleMaterials(AbsMaterial generalMaterial, int numberOfItems) {
        Integer returnQuantity = 0;
//        System.out.println("Providing " + generalMaterial.getName() + " of quantity " +
//                localIngredients.get(generalMaterial));
        if ((localIngredients.get(generalMaterial) - numberOfItems) >= 0) {
            returnQuantity = numberOfItems;
            localIngredients.replace(generalMaterial, (localIngredients.get(generalMaterial) - returnQuantity));
        } else {
            returnQuantity = localIngredients.get(generalMaterial);
            localIngredients.replace(generalMaterial, 0);
        }
        System.out.println(this.getClass().getSimpleName() + " is providing " + generalMaterial.getName() + " in" +
                " quantity " + returnQuantity);
        return returnQuantity;
    }
}