package checkmate.simple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BoardResolver {
	
	// board size
	int w;
	int h;
	
	Map<Piece, Integer> pieces;
	
	public BoardResolver(int w, int h, Map<Piece, Integer> pieces) {
		this.w = w;
		this.h = h;
		this.pieces = pieces;
	}

	
	public void resolve() {
		// map of combination of pieces and remaining valid squares
		Map<Combination, ValidSquares> valid = null;
		// all the squares in the board available
		ValidSquares squares = new ValidSquares();
		// for each piece enrich current combinations
		for (Piece piece : pieces.keySet()) {
			int pieceNum = pieces.get(piece);
			if (valid == null) {
				// init of squares
				for (int i = 0; i<w*h; i++) {
					squares.add(i);
				}
				// init of valid pieces
				valid = piece.filter(null, squares, pieceNum);
				continue;
			}
			// new and outdates combinations (with their valid squares)
			Set<Combination> keysRemove = new HashSet<Combination>();
			Map<Combination, ValidSquares> entryAdd = new HashMap<Combination, ValidSquares>();
			
			// for each existing valid combination add new ones to add list
			// and add itself to delete list (list used to not modify valid during the iteration)
			for (Combination combination : valid.keySet()) {
				entryAdd.putAll(piece.filter(combination, valid.get(combination), pieceNum));
				keysRemove.add(combination);
			}

			//remove outdated and add new results
			for (Combination k : keysRemove) {
				valid.remove(k);
			}
			valid.putAll(entryAdd);
		}
	}

}
