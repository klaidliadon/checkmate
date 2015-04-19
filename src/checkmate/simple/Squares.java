package checkmate.simple;

import java.util.HashSet;

public class Squares extends HashSet<Integer> {

	private static final long serialVersionUID = 3378817051333279676L;
	
	public final int w;
	public final int h;
	
	public Squares(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public Squares menace(Squares menaced) {
		Squares newSquares = new Squares(w, h);
		for (Integer i : this) {
			if (!menaced.contains(i)) newSquares.add(i);
		}
		return newSquares;
	}

}
