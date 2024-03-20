package Commons.Dice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Board {
    static String path = "C:\\test\\results";
    static String extension = ".csv";
    static String separator = ",";
    public static void main(String[] args) {
        File file = new File(path + "output" + extension);

        Dice die = new Die(new double[]{0.5, 0.25, 0.125, 0.0625, 0.03125, 0.03125});
        //Dice die = new Die(6);
        DiceBag bag = new DiceBag(die,256);
        //current time
        long startTime = System.currentTimeMillis();
        executeComputation(bag, 100000, 1000, 250, 10, file, new int[]{0, 1000});
        //end time
        long endTime = System.currentTimeMillis();
        //result en minutes:second:millisecond form
        long result = endTime - startTime;
        System.out.println("Time: " + result + "ms");

    }

    public static void executeComputation(Dice dice, int nbRolls, int workers, int workerMemoryLimit, int allocatedThreads, File outputFile, int[] ranges){
       //use map reduce 
        ConcurrentHashMap<String, int[]> simulationResults = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(allocatedThreads);
        ArrayList<Simulation> simulations = new ArrayList<>();
        for (int i = 0; i < workers; i++) {
            String name = "Worker " + i;
            Simulation simulation = new Simulation(name, dice, nbRolls, simulationResults);
            simulations.add(simulation);
        }
        for (int i = 0; i < workers; i++) {
            executor.execute(simulations.get(i));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        String content = countedString(simulationResults, ranges[0], ranges[1]);
        writeResultsToFile(outputFile, content);
        
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
            sb.append(i + separator + counted.get(i) + "\n");
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
        try (Stream<String> lines = Files.lines(Paths.get(file.getPath()))) {
            lines.skip(1) // skip the first line
                .map(line -> line.split(separator)) // split the line into the number and the count
                .forEach(parts -> counted.put(Integer.parseInt(parts[0]), Long.parseLong(parts[1]))); // add the number and the count to the map
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counted;
    }
}
