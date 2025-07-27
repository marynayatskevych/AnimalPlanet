package util;

import entity.Entity;
import entity.Plant;
import entity.animals.herbivore.*;
import entity.animals.predator.*;

import java.util.HashMap;
import java.util.Map;

public class DietTable {
    private static final Map<Class<? extends Entity>, Map<Class<? extends Entity>, Integer>> table = new HashMap<>();

    static {
        Map<Class<? extends Entity>, Integer> wolfDiet = new HashMap<>();
        wolfDiet.put(Horse.class, 10);
        wolfDiet.put(Deer.class, 15);
        wolfDiet.put(Rabbit.class, 60);
        wolfDiet.put(Mouse.class, 80);
        wolfDiet.put(Goat.class, 60);
        wolfDiet.put(Sheep.class, 70);
        wolfDiet.put(Boar.class, 15);
        wolfDiet.put(Buffalo.class, 10);
        wolfDiet.put(Duck.class, 40);
        table.put(Wolf.class, wolfDiet);

        Map<Class<? extends Entity>, Integer> boaDiet = new HashMap<>();
        boaDiet.put(Fox.class, 15);
        boaDiet.put(Rabbit.class, 20);
        boaDiet.put(Mouse.class, 40);
        boaDiet.put(Duck.class, 10);
        table.put(Python.class, boaDiet);

        Map<Class<? extends Entity>, Integer> foxDiet = new HashMap<>();
        foxDiet.put(Rabbit.class, 70);
        foxDiet.put(Mouse.class, 90);
        foxDiet.put(Duck.class, 60);
        table.put(Fox.class, foxDiet);

        Map<Class<? extends Entity>, Integer> bearDiet = new HashMap<>();
        bearDiet.put(Horse.class, 40);
        bearDiet.put(Deer.class, 80);
        bearDiet.put(Rabbit.class, 80);
        bearDiet.put(Mouse.class, 90);
        bearDiet.put(Goat.class, 70);
        bearDiet.put(Sheep.class, 70);
        bearDiet.put(Boar.class, 50);
        bearDiet.put(Buffalo.class, 20);
        bearDiet.put(Duck.class, 10);
        table.put(Bear.class, bearDiet);

        Map<Class<? extends Entity>, Integer> eagleDiet = new HashMap<>();
        eagleDiet.put(Fox.class, 10);
        eagleDiet.put(Rabbit.class, 90);
        eagleDiet.put(Mouse.class, 90);
        eagleDiet.put(Duck.class, 80);
        table.put(Eagle.class, eagleDiet);

        addPlantEater(Horse.class);
        addPlantEater(Deer.class);
        addPlantEater(Rabbit.class);
        addPlantEater(Mouse.class);
        addPlantEater(Goat.class);
        addPlantEater(Sheep.class);
        addPlantEater(Boar.class);
        addPlantEater(Buffalo.class);
        addPlantEater(Caterpillar.class);

        Map<Class<? extends Entity>, Integer> duckDiet = new HashMap<>();
        duckDiet.put(Caterpillar.class, 90);
        duckDiet.put(Plant.class, 100);
        table.put(Duck.class, duckDiet);

        Map<Class<? extends Entity>, Integer> caterpillarDiet = new HashMap<>();
        caterpillarDiet.put(Plant.class, 100);
        table.put(Caterpillar.class, caterpillarDiet);
    }

    private static void addPlantEater(Class<? extends Entity> herbivoreClass) {
        Map<Class<? extends Entity>, Integer> diet = new HashMap<>();
        diet.put(Plant.class, 100);
        table.put(herbivoreClass, diet);
    }

    public static int getChance(Class<? extends Entity> predator, Class<? extends Entity> prey) {
        Map<Class<? extends Entity>, Integer> diet = table.get(predator);
        if (diet != null) {
            return diet.getOrDefault(prey, 0);
        }
        return 0;
    }
}
