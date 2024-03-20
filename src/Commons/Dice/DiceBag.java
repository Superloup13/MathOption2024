package Commons.Dice;

import java.util.ArrayList;
import java.util.Arrays;

public class DiceBag implements Dice {
    private ArrayList<Dice> dices;

    public DiceBag(int nbFaces, int nbDices){
        this.dices = new ArrayList<>();
        for (int i = 0; i < nbDices; i++){
            this.dices.add(new Die(nbFaces));
        }
    }

    public DiceBag(Dice... dices){
        this.dices = new ArrayList<>();
        this.dices.addAll(Arrays.asList(dices));
    }

    public int getNbDices(){
        return this.dices.size();
    }

    public int roll(){
        int result = 0;
        for (Dice dice : this.dices){
            result += dice.roll();
        }
        return result;
    }


}
