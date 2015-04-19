package checkmate.simple;

import java.util.Map;

import org.paukov.combinatorics.CombinatoricsVector;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

public enum Piece {
	QUEEN, ROOK, BISHOP, KING, KNIGTH;
	
	public Map<Combination, Squares> filter(Combination pieces, Squares squares, int number) {	
		Generator<Integer> generator = Factory.createSimpleCombinationGenerator(new CombinatoricsVector<Integer>(squares), number);
		for (ICombinatoricsVector<Integer> permutation : generator) {
			//TODO find the spots menaced by the permutation 
			//TODO if any menaced spot is in the permutation skip
			//TODO if any menaced spot is in the current combination skip
			//TODO create a new entry in the result (key=pieces+permutation, value=squares-menaced)
		}
		return null;
	}
}