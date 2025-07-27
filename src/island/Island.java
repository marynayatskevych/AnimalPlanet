package island;

import entity.Entity;
import entity.Plant;
import entity.animals.Animal;
import entity.animals.herbivore.*;
import entity.animals.predator.*;

import java.util.Random;

public class Island {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 20;
    private final Location[][] locations = new Location[HEIGHT][WIDTH];
    private final Random random = new Random();

    private static Island instance;
    public static Island getInstance() {
        return instance;
    }
    public Island() {
        instance = this;
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                locations[row][col] = new Location(row, col);
            }
        }
        initializeEntities();
    }
    private void initializeEntities() {
        spawnRandomly("Wolf", 30);
        spawnRandomly("Python", 30);
        spawnRandomly("Fox", 30);
        spawnRandomly("Bear", 30);
        spawnRandomly("Eagle", 20);
        spawnRandomly("Rabbit", 200);
        spawnRandomly("Horse", 20);
        spawnRandomly("Deer", 20);
        spawnRandomly("Goat", 140);
        spawnRandomly("Sheep", 140);
        spawnRandomly("Boar", 50);
        spawnRandomly("Buffalo", 10);
        spawnRandomly("Duck", 200);
        spawnRandomly("Mouse", 500);
        spawnRandomly("Plant", 1000);
    }

    private void spawnRandomly(String type, int count) {
        for (int i = 0; i < count; i++) {
            int row = random.nextInt(HEIGHT);
            int col = random.nextInt(WIDTH);
            Entity entity = createEntityByType(type);
            Location cell = locations[row][col];
            if (entity instanceof Animal animal) {
                animal.setCurrentLocation(cell);
            }
            cell.addEntity(entity);
        }
    }

    private Entity createEntityByType(String type) {
        return switch (type) {
            case "Wolf" -> new Wolf();
            case "Python" -> new Python();
            case "Fox" -> new Fox();
            case "Bear" -> new Bear();
            case "Eagle" -> new Eagle();
            case "Rabbit" -> new Rabbit();
            case "Horse" -> new Horse();
            case "Deer" -> new Deer();
            case "Goat" -> new Goat();
            case "Sheep" -> new Sheep();
            case "Boar" -> new Boar();
            case "Buffalo" -> new Buffalo();
            case "Duck" -> new Duck();
            case "Mouse" -> new Mouse();
            case "Plant" -> new Plant();
            default -> throw new IllegalArgumentException("Unknown entity type: " + type);
        };
    }

    public Location[][] getLocations() {
        return locations;
    }
}
