package checkmate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
	
	// board size
	int w;
	int h;
	
	Map<Piece, Integer> pieces;
	
	public Board(int w, int h, Map<Piece, Integer> pieces) {
		this.w = w;
		this.h = h;
		this.pieces = pieces;
	}

	/**
	 * For each piece type and number (e.g. 2 Queens) calculates the
	 * combinations that are not menacing any other piece on the board. Each
	 * combination is associated with the list of the squares that not menaced,
	 * this list is used to place the following pieces. While the number of
	 * valid partial combinations grows, the number of free squares shrinks.
	 * 
	 * @return a map of combinations and not menaced squares
	 */
	public Map<Combination, Squares> resolve() {
		// map of combination of pieces and remaining valid squares
		Map<Combination, Squares> valid = null;
		// all the squares available (not menaced and not occupied) in the board
		Squares squares = new Squares(w, h);
		Squares squaresOct = new Squares(w, h);
		for (int x = 0; x<w; x++) {
			for (int y=0; y<h; y++) {
				int v = x*w+y;
				squares.add(v);
				if (x<=(w/2+1) && y>=x && y<(h/2+1)) {
					squaresOct.add(v);
				}
			}
		}
		// for each piece enrich current combinations
		for (Piece piece : pieces.keySet()) {
			int pieceNum = pieces.get(piece);
			if (valid == null) {
				// init of valid pieces
				valid = piece.append(null, squares, pieceNum, null);
			} else {
				if (valid.isEmpty()) {
					return valid;
				}
				// new and outdated combinations (with their valid squares)
				Set<Combination> keysRemove = new HashSet<Combination>();
				Map<Combination, Squares> newValid = new HashMap<Combination, Squares>();
				
				// for each existing valid combination add new ones to add list
				// and add itself to delete list (list used to not modify valid during the iteration)
				for (Combination combination : valid.keySet()) {
					Map<Combination, Squares> toAdd = piece.append(combination, valid.get(combination), pieceNum, null);
					for (Combination k : toAdd.keySet()) {
						Squares free = new Squares(w, h);
						for (Integer i : toAdd.get(k)) {
							free.add(i);
						} 
						newValid.put(k, free );
					}
					keysRemove.add(combination);
				}
	
				//remove outdated and add new results
				valid = newValid;
			}
		}
		return valid;
	}

}
