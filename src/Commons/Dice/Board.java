package Commons.Dice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
    static String path = "C:\\MathOption2024\\src\\Commons\\Dice\\output\\";
    static String extension = ".csv";
    static String separator = ";";
    public static void main(String[] args) {
        File file = new File(path + "output" + extension);

        // Configure this !!
        // example :
        // DiceBag bag = new DiceBag(new Die(6), 2) // 2 dice with 6 faces
        // int[] ranges = {2, 12} // the range of the results
        // ====
        DiceBag bag = new DiceBag(new Die(new double[]{0.400,0.05,0.05,0.05,0.05,0.400}), 100);
        int[] ranges = {100, 600};
        // ====
        //clear the files in the folder
        File folder = new File(path);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (file.getName().endsWith(".csv")) {
                    f.delete();
                }
            }
        }
        //current time
        long startTime = System.currentTimeMillis();
        executeComputation(bag, 10000, 10000, 1000, 10, file, ranges);
        //end time
        long endTime = System.currentTimeMillis();
        //result en minutes:second:millisecond form
        long result = endTime - startTime;
        System.out.println("Time: " + result + "ms");

    }

    public static void executeComputation(Dice dice, int nbRolls, int workers, int workerMemoryLimit, int allocatedThreads, File outputFile, int[] ranges){
       //use map reduce 
        ConcurrentHashMap<String, int[]> simulationResults = new ConcurrentHashMap<>();
        ArrayList<File> files = new ArrayList<>();

        while (workers > 0){
            ExecutorService executor = Executors.newFixedThreadPool(allocatedThreads);
            ArrayList<Simulation> simulations = new ArrayList<>();
            for (int i = 0; i < workerMemoryLimit; i++) {
                String name = "Worker " + i;
                Simulation simulation = new Simulation(name, dice, nbRolls, simulationResults);
                simulations.add(simulation);
            }
            for (int i = 0; i < workerMemoryLimit; i++) {
                executor.execute(simulations.get(i));
                workers--;
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
            String content = countedString(simulationResults, ranges[0], ranges[1]);
            File file = new File(path + "intermediary_" + files.size() + extension);
            files.add(file);
            writeToFile(file, content);
        }

        Map<Integer, Long> counted = regroupIntermediaries(files);
        writeToFile(outputFile, addMedianAndAvg(writeValues(ranges[0], ranges[1], counted), counted));
    }

    /**
     * This method takes a ConcurrentHashMap of simulation results and returns a CSV string of the results
     * @param simulationResults the ConcurrentHashMap of simulation results
     * @param rangeLow the lowest number to be included in the CSV
     * @param rangeHigh the highest number to be included in the CSV
     * @return a CSV string of the results
     */
    public static String countedString(ConcurrentHashMap<String, int[]> simulationResults, int rangeLow, int rangeHigh) {
        HashMap<Integer, Long> counted = new HashMap<>();
        for (int i = rangeLow; i <= rangeHigh; i++) {
            counted.put(i, 0L);
        }
        for (String key : simulationResults.keySet()) {
            for (int result : simulationResults.get(key)) {
                counted.put(result, counted.getOrDefault(result, 0L) + 1);
            }
        }
        return writeValues(rangeLow, rangeHigh, counted);
    }

    public static String writeValues(int rangeLow, int rangeHigh, Map<Integer, Long> counted) {
        StringBuilder sb = new StringBuilder();
        sb.append("Number;Count\n");
        for (int i = rangeLow; i <= rangeHigh; i++) {
            sb.append(i + separator + counted.get(i) + "\n");
        }
        return sb.toString();
    }

    public static String addMedianAndAvg(String csv, Map<Integer, Long> counted) {
        // find the total number of occurences
        long totalOccurences = totalOccurences = counted.values().stream().mapToLong(Long::longValue).sum();
        // find the average
        long average = counted.entrySet().stream().mapToLong(entry -> entry.getKey() * entry.getValue()).sum() / totalOccurences;
        // find the median
        long median = 0;
        long halfOccurence = totalOccurences / 2;
        long currentSum = 0;
        ArrayList<Integer> keys = new ArrayList<>(counted.keySet());
        while (median == 0) {
            for (int i = 0; i < keys.size(); i++) {
                currentSum += counted.get(keys.get(i));
                if (currentSum >= halfOccurence) {
                    // when the median is found, break the loop
                    // account for the initial offset of the list by getting the smallest key
                    median = keys.get(0) + i;
                    break;
                }
            }
        }
        return csv.substring(0, csv.indexOf("\n") + 1) + "Average" + separator + average + "\nMedian" + separator + median + "\n" + csv.substring(csv.indexOf("\n") + 1);
    }

    public static void writeToFile(File file, String content) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, Long> regroupIntermediaries(List<File> files) {
    return files.parallelStream()
        .map(Board::readResultsFromFile)
        .flatMap(map -> map.entrySet().stream())
        .collect(Collectors.toConcurrentMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            Long::sum));
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
