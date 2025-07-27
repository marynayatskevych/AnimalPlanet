package entity.animals;

import entity.Entity;
import util.DietTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Herbivore extends Animal {

    public Herbivore(String name, double weight, int maxCountPerCell, int speed, double maxFoodNeeded) {
        super(name, weight, maxCountPerCell, speed, maxFoodNeeded);
    }

    @Override
    public void eat() {
        if (currentLocation == null) return;

        List<Entity> entities = new ArrayList<>(currentLocation.getEntities());
        Random random = new Random();

        for (Entity entity : entities) {
            if (this == entity) continue;

            int chance = DietTable.getChance(this.getClass(), entity.getClass());
            if (chance > 0) {
                int roll = random.nextInt(100);
                if (roll < chance) {
                    synchronized (currentLocation) {
                        currentLocation.removeEntity(entity);
                        this.addFood(entity.getWeight());
                        System.out.println(this.name + " ate " + entity.getName());
                    }
                    if (!this.isHungry()) break;
                }
            }
        }
    }
    @Override
    public void reproduce() {
        if (currentLocation == null) return;

        long sameTypeCount = currentLocation.getEntities().stream()
                .filter(e -> e.getClass() == this.getClass())
                .count();

        if (sameTypeCount >= 2 && sameTypeCount < this.maxCountPerCell) {
            try {
                Animal offspring = this.getClass().getDeclaredConstructor().newInstance();
                currentLocation.addEntity(offspring);
                offspring.setCurrentLocation(currentLocation);
                System.out.println(this.name + " reproduced in cell [" + currentLocation.getRow() + "," + currentLocation.getCol() + "]");
            } catch (Exception e) {
                System.err.println("Failed to reproduce: " + e.getMessage());
            }
        }
    }
}
