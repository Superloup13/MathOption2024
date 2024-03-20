package MathiasOthers.matrice;

import java.util.Arrays;
import java.util.Random;
import java.util.function.BiFunction;

/*
 *  Leo Studer spring semester 2024
 */

public abstract class LA {

    private final static int MAX_NB_VALUES_DISPLAYED = 11;
    private final static double N_ZERO = 10.E-15;

    public static class LAException extends Exception {
        LAException(String message) {
            super(message);
        }
    }

    public static String toString(double[] vector) {
        if (vector == null) return "toString: empty vector !!!";
        int nbValues = vector.length;
        StringBuilder stringBuilder = new StringBuilder("[");
        if (nbValues <= MAX_NB_VALUES_DISPLAYED) {
            for (double value : vector)
                stringBuilder.append(String.format(" %.3f ", value));
        } else {
            stringBuilder.append(String.format(" %.3f ", vector[0]));
            stringBuilder.append(String.format(" %.3f ", vector[1]));
            stringBuilder.append(String.format(" %.3f ", vector[2]));
            stringBuilder.append("  ... ").append(nbValues - 6).append(" values suppressed ...  ");
            stringBuilder.append(String.format(" %.3f ", vector[nbValues - 3]));
            stringBuilder.append(String.format(" %.3f ", vector[nbValues - 2]));
            stringBuilder.append(String.format(" %.3f ", vector[nbValues - 1]));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String toString(double[][] matrix) {
        if (matrix == null) return "toString: empty matrix !!! ";
        int nbRows = matrix.length;
        int row;
        StringBuilder stringBuilder = new StringBuilder("[");
        if (nbRows <= MAX_NB_VALUES_DISPLAYED) {
            for (row = 0; row < nbRows-1; row++)
                stringBuilder.append(toString(matrix[row])).append("\n");
            stringBuilder.append(toString(matrix[row]));
        } else {
            stringBuilder.append(toString(matrix[0])).append("\n");
            stringBuilder.append(toString(matrix[1])).append("\n");
            stringBuilder.append(toString(matrix[2])).append("\n");
            stringBuilder.append("[ .....................\n");
            stringBuilder.append("[     ").append(nbRows - 6).append(" rows suppressed\n");
            stringBuilder.append("[ .....................\n");
            stringBuilder.append(toString(matrix[nbRows - 3])).append("\n");
            stringBuilder.append(toString(matrix[nbRows - 2])).append("\n");
            stringBuilder.append(toString(matrix[nbRows - 1]));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static void showArray(double[] vector) {
        System.out.println(toString(vector)+"\n");
    }

    public static void showVector(double[] vector) { showArray(vector); }

    public static void showVector(double[][] vector) { showArray(vector); }

    public static void showArray(double[][] matrix) {
        System.out.println(toString(matrix)+"\n");
    }

    public static void showMatrix(double[][] matrix) {
        showArray(matrix);
    }


    public static double[][] randomMatrix(int nbRows, int nbColumns) {
        // all values are between -1 and +1
        Random randomGenerator = new Random();
        double[][] randomMatrix = new double[nbRows][nbColumns];
        int row, column;
        for (row = 0; row < nbRows; row++)
            for (column = 0; column < nbColumns; column++)
                randomMatrix[row][column] = randomGenerator.nextDouble() * 2.0 - 1.0;
        return randomMatrix;
    }


    public static double[] copyOf(double[] vector) {
        return Arrays.copyOf(vector, vector.length);
    }

    public static double[][] copyOf(double[][] matrix) {
        double[][] copy = new double[matrix.length][];
        for (int row = 0; row < matrix.length; row++)
            copy[row] = copyOf(matrix[row]);
        return copy;
    }
}
