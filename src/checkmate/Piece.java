package checkmate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.paukov.combinatorics.CombinatoricsVector;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.ICombinatoricsVector;

public enum Piece {
	QUEEN, ROOK, BISHOP, KING, KNIGHT;
	
	public static Map<String, Piece> NAME_SET;
	
	static {
		NAME_SET = new LinkedHashMap<String, Piece>();
		NAME_SET.put("queen", Piece.QUEEN);
		NAME_SET.put("rook", Piece.ROOK);
		NAME_SET.put("bishop", Piece.BISHOP);
		NAME_SET.put("knight", Piece.KNIGHT);
		NAME_SET.put("king", Piece.KING);
	}
	
	public Map<Combination, Squares> append(Combination pieces, Squares squares, int number, Iterable<ICombinatoricsVector<Integer>> generator) {
		Map<Combination, Squares> result = new HashMap<Combination, Squares>();
		if (generator == null) {
			generator = Factory.createSimpleCombinationGenerator(new CombinatoricsVector<Integer>(squares), number);
		}
	permutations: 
		for (Iterable<Integer> permutation : generator) {
			Combination newComb = new Combination(squares.w, squares.h);
			if (pieces != null) {
				for (Integer i : pieces) {
					newComb.add(i);
				}
			}
			Squares free = new Squares(squares.w, squares.h);
			for (Integer i : squares) {
				free.add(i);
			}
			for (Integer pos : permutation) {
				// find the spots menaced by the permutation
				free = findFree(newComb, free, pos);
				if (free == null || free.isEmpty()) {
					continue permutations;
				}
				// add the new piece to combination
				newComb.add(pos);
			}
			for (Combination alt : newComb.getAlternative()) {
				if (result.containsKey(alt)) {
					continue permutations;
				}
			}
			result.put(newComb, free);
		}
		return result;
	}

	Squares findFree(Combination comb, Squares sq, Integer p) {
		int px = sq.x(p);
		int py = sq.y(p);
		Squares newSq = new Squares(sq.w, sq.h);
		// if any menaced spot is in the current combination (including new pieces)
		for (int i : comb) {
			int ix = sq.x(i);
			int iy = sq.y(i);
			if (isMenaced(ix, iy, px, py)) return null;
		}
		for (int i : sq) {
			int ix = sq.x(i);
			int iy = sq.y(i);
			if (!isMenaced(ix, iy, px, py)){
				newSq.add(i);
			}
		}
		return newSq;
	}
	
	boolean isMenaced(int ix, int iy, int px, int py) {
		int dx = Math.abs(ix-px);
		int dy = Math.abs(iy-py);
		if (dx == 0 && dy == 0) return true;
		switch (this) {
		case BISHOP:
			// diagonal
			return dx == dy;
		case KING:
			// adjacent
			return dx < 2 && dy < 2; 
		case KNIGHT:
			// special
			return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
		case QUEEN:
			// diagonal and orthogonal
			return (dx == 0 ^ dy == 0) || dx == dy;
		case ROOK:
			// orthogonal
			return dx == 0 ^ dy == 0;
		}
		return false;
	}
}