package sample.warehouse;

import sample.delay.DelayUtil;
import sample.material.AbsMaterial;

public class DistantWarehouse implements Warehouse {

    DelayUtil delayUtil;

    private AbsMaterial absMaterial;

    private int numberOfItems;


    public AbsMaterial getAbsMaterial() {
        return absMaterial;
    }

    public void setAbsMaterial(AbsMaterial absMaterial) {
        this.absMaterial = absMaterial;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    @Override
    public Integer provideQuantityOfOneMaterial(AbsMaterial generalMaterial) {
        return null;
    }

    @Override
    public synchronized Integer provideMultipleMaterials(AbsMaterial generalMaterial, int numberOfItems) {
        double waitingTime = DelayUtil.getRandomDoubleBetweenRange(1000, 3000);
        System.out.println(this.getClass().getSimpleName() + " Giving " + generalMaterial.getName() + " of quantity "
                + numberOfItems);
        return numberOfItems;
    }


}

