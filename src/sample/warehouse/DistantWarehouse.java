package sample.warehouse;

import sample.material.AbsMaterial;

import java.util.List;

public class DistantWarehouse implements Warehouse {

    @Override
    public Integer provideQuantityOfOneMaterial(AbsMaterial generalMaterial) {
        return null;
    }

    @Override
    public Integer provideMultipleMaterials(AbsMaterial generalMaterial, int numberOfItems) {
        return null;
    }
}
