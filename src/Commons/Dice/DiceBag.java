package Commons.Dice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DiceBag implements Dice {
    private ArrayList<Dice> dices;

    public DiceBag(Dice dice, int nbDices) {
        this.dices = new ArrayList<>(Collections.nCopies(nbDices, dice));
    }
    
    public DiceBag(Dice... dices){
        this.dices = new ArrayList<>();
        this.dices.addAll(Arrays.asList(dices));
    }

    public int getNbDices(){
        return this.dices.size();
    }

    public int roll() {
        return this.dices.stream().mapToInt(Dice::roll).sum();
    }

    @Override
    public int getSides() {
    
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
