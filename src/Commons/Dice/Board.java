package Commons.Dice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class Board {
    public static void main(String[] args) {
        File file = new File("C:\\Mathoption\\src\\Projet\\results.csv");

        //Dice die = new Die(new double[]{0.5, 0.25, 0.125, 0.0625, 0.03125, 0.03125});
        Dice die = new Die(2);
        Dice d2 = new DiceBag(die,die);
        Dice d4 = new DiceBag(d2,d2);
        Dice d8 = new DiceBag(d4,d4);
        Dice d16 = new DiceBag(d8,d8);
        Dice d32 = new DiceBag(d16,d16);
        Dice d64 = new DiceBag(d32,d32);
        Dice d128 = new DiceBag(d64,d64);
        Dice d256 = new DiceBag(d128,d128);

        executeComputation(d256, 1000, 1000, 100, 10, file, new int[]{0, 1000});
    }

    public static void executeComputation(Dice dice, int nbRolls, int workers, int workerMemoryLimit, int allocatedThreads, File outputFile, int[] ranges){
        ConcurrentHashMap<String, int[]> simulationResults = new ConcurrentHashMap<>();
        ArrayList<File> files = new ArrayList<>();

        int workersToBeCreated = workers;

        while (workersToBeCreated != 0) {
            ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(allocatedThreads);
            for (int i = 0; i < workerMemoryLimit && workersToBeCreated != 0; i++) {
                Simulation simulation = new Simulation("Simulation" + i, dice, nbRolls, simulationResults);
                executor.execute(simulation);
                workersToBeCreated--;
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                // wait for all threads to finish
            }
            // write results to file
            File file = new File("C:\\Mathoption\\src\\Projet\\results" + files.size() + ".csv");
            files.add(file);
            writeResultsToFile(file, countedString(simulationResults, ranges[0], ranges[1]));
        }

        System.out.println("Finished computation. Reassembling results...");

        // reassemble results
        ArrayList<HashMap<Integer, Long>> reassembledResults = new ArrayList<>();
        for (File f : files) {
            reassembledResults.add(readResultsFromFile(f));
        }
        // reorganize results
        for (int i = 1; i < reassembledResults.size(); i++) {
            // add the results of the i-th file to the first file
            for (int key : reassembledResults.get(i).keySet()) {
                if (reassembledResults.get(0).containsKey(key)) {
                    reassembledResults.get(0).put(key, reassembledResults.get(0).get(key) + reassembledResults.get(i).get(key));
                } else {
                    reassembledResults.get(0).put(key, reassembledResults.get(i).get(key));
                }
            }
        }
        // write the reassembled results to the output file
        writeResultsToFile(outputFile, writeValues(ranges[0], ranges[1], reassembledResults.get(0)));
    }

    public static String countedString(ConcurrentHashMap<String, int[]> simulationResults, int rangeLow, int rangeHigh) {
        HashMap<Integer, Long> counted = new HashMap<>();
        for (int i = rangeLow; i <= rangeHigh; i++) {
            counted.put(i, 0L);
        }
        for (String key : simulationResults.keySet()) {
            for (int result : simulationResults.get(key)) {
                counted.put(result, counted.get(result) + 1);
            }
        }
        return writeValues(rangeLow, rangeHigh, counted);
    }

    public static String writeValues(int rangeLow, int rangeHigh, HashMap<Integer, Long> counted) {
        StringBuilder sb = new StringBuilder();
        sb.append("Number;Count\n");
        for (int i = rangeLow; i <= rangeHigh; i++) {
            sb.append(i + ";" + counted.get(i) + "\n");
        }
        return sb.toString();
    }

    public static void writeResultsToFile(File file, String content) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, Long> readResultsFromFile(File file) {
        HashMap<Integer, Long> counted = new HashMap<>();
        try {
            java.util.Scanner scanner = new java.util.Scanner(file);
            scanner.nextLine(); // skip the first line
            while (scanner.hasNextLine()) {
                // split the line into the number and the count
                String[] line = scanner.nextLine().split(";");
                // remove the \n at the end of the line
                line[1] = line[1].replace("\n", "");
                // add the number and the count to the map
                counted.put(Integer.parseInt(line[0]), Long.parseLong(line[1]));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return counted;
    }
}
