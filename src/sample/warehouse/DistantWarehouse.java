package sample.warehouse;

import sample.material.AbsMaterial;

import java.util.List;

public class DistantWarehouse implements Warehouse {

    @Override
    public AbsMaterial provideOneMaterial(AbsMaterial generalMaterial, int secondsOfDelay) {
        return null;
    }

    @Override
    public List<AbsMaterial> provideMultipleMaterials(AbsMaterial generalMaterial, int numberOfItems, int secondsOfDelay) {
        return null;
    }
}
