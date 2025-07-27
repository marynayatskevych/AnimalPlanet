package entity.animals.herbivore;

import entity.animals.Animal;
import entity.animals.Herbivore;

public class Sheep extends Herbivore {

    public Sheep() {
        super("Sheep", 70.0, 140, 3, 15.0);
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
