package entity.animals;

import entity.Entity;
import island.Island;
import island.Location;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Entity {
    protected int maxCountPerCell;
    protected int speed;
    protected double maxFoodNeeded;
    protected double currentFood;
    protected volatile Location currentLocation;
    protected Island island;


    public Animal(String name, double weight, int maxCountPerCell, int speed, double maxFoodNeeded) {
        super(name, weight);
        this.maxCountPerCell = maxCountPerCell;
        this.speed = speed;
        this.maxFoodNeeded = maxFoodNeeded;
        this.currentFood = 0;
    }

    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    public abstract void eat();
    public abstract void reproduce();

    public boolean isHungry() {
        return currentFood < maxFoodNeeded;
    }

    public void addFood(double food) {
        currentFood += food;
        if (currentFood > maxFoodNeeded) {
            currentFood = maxFoodNeeded;
        }
    }

    public void move() {
        if (currentLocation == null || island == null) return;

        Location[][] map = island.getLocations();

        int currentRow = currentLocation.getRow();
        int currentCol = currentLocation.getCol();

        ThreadLocalRandom random = ThreadLocalRandom.current();
        int newRow = Math.max(0, Math.min(Island.HEIGHT - 1, currentRow + random.nextInt(-speed, speed + 1)));
        int newCol = Math.max(0, Math.min(Island.WIDTH - 1, currentCol + random.nextInt(-speed, speed + 1)));

        if (newRow == currentRow && newCol == currentCol) return;

        Location newLocation = map[newRow][newCol];

        long sameTypeCount = newLocation.getEntities().stream()
                .filter(e -> e.getClass() == this.getClass())
                .count();

        if (sameTypeCount < this.maxCountPerCell) {
            Object firstLock = currentLocation.hashCode() < newLocation.hashCode() ? currentLocation : newLocation;
            Object secondLock = firstLock == currentLocation ? newLocation : currentLocation;

            synchronized (firstLock) {
                synchronized (secondLock) {
                    currentLocation.removeEntity(this);
                    newLocation.addEntity(this);
                    this.setCurrentLocation(newLocation);
                    System.out.println(name + " moved to [" + newRow + "," + newCol + "]");
                }
            }
        }
    }
}
