package island;

import entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private final List<Entity> entities = new ArrayList<>();
    private final int row;
    private final int col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
        System.out.println(entity.getName() + " was removed from cell");
    }

    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    @Override
    public String toString() {
        return "Entities: " + entities;
    }
}

