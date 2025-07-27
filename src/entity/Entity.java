package entity;

public abstract class Entity {
    protected String name;
    protected double weight;

    public Entity(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return name;
    }
}
