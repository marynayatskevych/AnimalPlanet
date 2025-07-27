package main;

import island.Island;
import simulation.SimulationManager;

public class Main {
    public static void main(String[] args) {
        Island island = new Island();
        SimulationManager manager = new SimulationManager(island);
        manager.startSimulation();
    }
}

