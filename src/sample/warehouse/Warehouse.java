package sample.warehouse;

import sample.material.AbsMaterial;

public interface Warehouse {

    Integer provideMultipleMaterials(AbsMaterial generalMaterial, int numberOfItems);

}
