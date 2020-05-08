package sample.warehouse;

import sample.material.AbsMaterial;

import java.util.List;

public interface Warehouse {

    Integer provideQuantityOfOneMaterial(AbsMaterial generalMaterial);

    Integer provideMultipleMaterials(AbsMaterial generalMaterial, int numberOfItems);


}
