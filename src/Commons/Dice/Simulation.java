package Commons.Dice;

import java.util.concurrent.ConcurrentHashMap;

public class Simulation extends Thread{
    String name;
    Dice dice;
    int nbRolls;
    ConcurrentHashMap<String, int[]> recordsToWriteTo;
    int[] results;

    public Simulation(String name, Dice dice, int nbRolls, ConcurrentHashMap<String, int[]> recordsToWriteTo){
        this.name = name;
        this.dice = dice;
        this.nbRolls = nbRolls;
        this.results = new int[nbRolls];
        this.recordsToWriteTo = recordsToWriteTo;
    }

    public void run() {
        for (int i = 0; i < nbRolls; i++) {
            results[i] = dice.roll();
        }
        // add the results to the simulationResults
        recordsToWriteTo.put(name, results);
        System.out.println(name + "\t\t Finished writing to recordsToWriteTo.");
    }
}
