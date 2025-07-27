package entity.animals.herbivore;

import entity.Entity;
import entity.animals.Herbivore;
import util.DietTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Duck extends Herbivore {

    public Duck() {
        super("Duck", 1.0, 200, 4, 0.15);
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
                        System.out.println(name + " ate " + entity.getName());
                    }
                    if (!this.isHungry()) break;
                }
            }
        }
    }
}
