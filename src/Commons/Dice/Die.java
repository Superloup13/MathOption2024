package Commons.Dice;

import java.util.concurrent.ThreadLocalRandom;

public class Die implements Dice {
    private double[] probabilityFaces;
    private double[] cumulativeProbabilityFaces;
    private int nbFaces;

    public Die(int nbFaces) {
        this.nbFaces = nbFaces;
        this.probabilityFaces = new double[nbFaces];
        for (int i = 0; i < nbFaces; i++) {
            this.probabilityFaces[i] = 1.0 / nbFaces;
        }
        this.computeCumulativeProbabilityFaces();
    }

    public Die(double[] probabilityFaces) {
        this.nbFaces = probabilityFaces.length;
        this.probabilityFaces = probabilityFaces;
        this.computeCumulativeProbabilityFaces();
    }

    public double getProbability(int face) {
        return this.probabilityFaces[face];
    }

    public void setProbability(int face, double probability) {
        this.probabilityFaces[face] = probability;
    }

    public int getNbFaces() {
        return this.nbFaces;
    }

    /**
     * Compute the cumulative probability of each face
     */
    private void computeCumulativeProbabilityFaces() {
        this.cumulativeProbabilityFaces = new double[this.nbFaces];
        this.cumulativeProbabilityFaces[0] = this.probabilityFaces[0];
        for (int i = 1; i < this.nbFaces; i++) {
            this.cumulativeProbabilityFaces[i] = this.cumulativeProbabilityFaces[i - 1] + this.probabilityFaces[i];
        }
    }

    /**
     * Roll the dice and return the result (from the cumulative probabilities)
     * Bounds : [1, nbFaces[
     * @return the result of the dice roll
     */
    public int roll() {
        double random = ThreadLocalRandom.current().nextDouble();

        int left = 0;
        int right = this.nbFaces - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (random < this.cumulativeProbabilityFaces[mid]) {
                if (mid == 0 || random >= this.cumulativeProbabilityFaces[mid - 1]) {
                    return mid + 1;
                }
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }

    @Override
    public int getSides() {
        return this.nbFaces;
    }
}
