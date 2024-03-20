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
    public int roll(){
        // return a random between 0 (inclusive) and 1 (exclusive) ([0,1[)
        double random = ThreadLocalRandom.current().nextDouble();
        // example dice probabilities: 0.16, 0.16, 0.16, 0.16, 0.16, 0.16
        // [0, 0.16[ , [0.16, 0.32[ , [0.32, 0.48[ , [0.48, 0.64[ , [0.64, 0.80[ , [0.80, 1.0[
        // if random is 0.5, then the result is 4

        // get the result of the dice roll from the cumulative probabilities
        for (int i = 0; i < this.nbFaces; i++) {
            if (random < this.cumulativeProbabilityFaces[i]) {
                return i+1;
            }
        }
        // if error, return -1
        return -1;
    }
}
