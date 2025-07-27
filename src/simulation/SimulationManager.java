package simulation;

import entity.Entity;
import entity.animals.Animal;
import island.Island;
import island.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class SimulationManager {

    private final Island island;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public SimulationManager(Island island) {
        this.island = island;
    }

    public void startSimulation() {
        scheduler.scheduleAtFixedRate(this::simulateStep, 0, 2, TimeUnit.SECONDS);
    }

    private void simulateStep() {
        System.out.println("\n🔁 Simulation step started...");
        simulateAnimals();
        growPlants();
        printStatistics();
    }

    private void simulateAnimals() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Location[][] map = island.getLocations();
        for (int row = 0; row < Island.HEIGHT; row++) {
            for (int col = 0; col < Island.WIDTH; col++) {
                int finalRow = row;
                int finalCol = col;

                executor.submit(() -> {
                    Location cell = map[finalRow][finalCol];
                    synchronized (cell) {
                        List<Entity> entities = new ArrayList<>(cell.getEntities());
                        for (Entity entity : entities) {
                            if (entity instanceof Animal animal) {
                                animal.move();
                                animal.eat();
                                animal.reproduce();
                            }
                        }
                    }
                });
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void growPlants() {
        Location[][] map = island.getLocations();
        for (int row = 0; row < Island.HEIGHT; row++) {
            for (int col = 0; col < Island.WIDTH; col++) {
                Location cell = map[row][col];
                synchronized (cell) {
                    cell.addEntity(new entity.Plant());
                }
            }
        }
        System.out.println("🌱 Plants have grown.");
    }

    private void printStatistics() {
        Map<String, Integer> stats = new HashMap<>();

        Location[][] map = island.getLocations();
        for (Location[] row : map) {
            for (Location cell : row) {
                for (Entity e : cell.getEntities()) {
                    stats.put(e.getName(), stats.getOrDefault(e.getName(), 0) + 1);
                }
            }
        }
        System.out.print("Total: ");

        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            String icon = switch (entry.getKey()) {
                case "Rabbit" -> "🐇";
                case "Wolf" -> "🐺";
                case "Plant" -> "🌿";
                case "Bear" -> "🐻";
                case "Fox" -> "🦊";
                case "Deer" -> "🦌";
                case "Duck" -> "🦆";
                case "Goat" -> "🐐";
                case "Horse" -> "🐎";
                case "Sheep" -> "🐑";
                case "Boar" -> "🐗";
                case "Buffalo" -> "🐃";
                case "Mouse" -> "🐁";
                case "Eagle" -> "🦅";
                case "Python" -> "🐍";
                default -> "❓";

            };
            System.out.printf("%s: %d, ", icon, entry.getValue());
        }
        System.out.println();
    }

}

