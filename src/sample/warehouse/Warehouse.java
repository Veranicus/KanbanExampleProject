package sample.warehouse;

import sample.material.AbsMaterial;

import java.util.List;

public interface Warehouse {

    Integer provideQuantityOfOneMaterial(AbsMaterial generalMaterial);

    List<AbsMaterial> provideMultipleMaterials(AbsMaterial generalMaterial, int numberOfItems);


}
