package checkmate.simple;

import java.util.HashSet;

public class Combination extends HashSet<Integer> {

	private static final long serialVersionUID = 3378817051333279676L;
	
	public Combination append(Combination combination) {
		Combination newCombination = new Combination();
		newCombination.addAll(this);
		newCombination.addAll(combination);
		return newCombination;
	}

}
