package checkmate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.paukov.combinatorics.CombinatoricsVector;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.ICombinatoricsVector;

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
		Squares quarter = new Squares(w, h);
		for (int x = 0; x<w; x++) {
			for (int y=0; y<h; y++) {
				int v = squares.xy(x, y);
				squares.add(v);
				if (x*2<w && y*2<h) {
					quarter.add(v);
				}
			}
		}
		
		System.out.println("Skipping simmetrical combinations:\n"
				+ "The first piece will be placed only in top right quarter of the board\n"
				+ "For every combination found in this way 3 more results are included:\n"
				+ "- mirrored on x axis\n"
				+ "- mirrored on y axis\n"
				+ "- mirrored on both axes");
	
		// for each piece enrich current combinations
		for (Piece piece : pieces.keySet()) {
			int pieceNum = pieces.get(piece);
			if (valid == null) {
				Iterable<ICombinatoricsVector<Integer>> generator = Factory.createSimpleCombinationGenerator(new CombinatoricsVector<Integer>(quarter), 1);
				valid = piece.append(null, squares, 1, generator);
				if (pieceNum>1){
					System.out.println(String.format("After %s (x1) - %d combinantions", piece, valid.size()));
					pieceNum--;
					Map<Combination, Squares> partial = addPiece(valid, piece, pieceNum);
					Set<Set<Integer>> track = new HashSet<Set<Integer>>();
					valid = new HashMap<Combination, Squares>();
					// filtering the positions that are mirrored versions of positions already added
					// ignoring order of pieces (using a simple HashSet) to find also scrambled combinations 
				results:
					for (Combination c : partial.keySet()) {
						Set<Integer> s = new HashSet<Integer>();
						s.addAll(c);
						if (track.contains(s)) {
							continue results;
						}
						Set<Set<Integer>> alts = new HashSet<Set<Integer>>();
						alts.add(s);
						for (Combination alt : c.getAlternative()) {
							Set<Integer> a = new HashSet<Integer>();
							a.addAll(alt);
							alts.add(a);
						}
						track.addAll(alts);	
						
						valid.put(c, partial.get(c));		
					}
				}
				
			} else {
				if (valid.isEmpty()) {
					return valid;
				}
				// new and outdated combinations (with their valid squares)
				valid = addPiece(valid, piece, pieceNum);
			}
			System.out.println(String.format("After %s (x%d) - %d combinantions", piece, pieceNum, valid.size()));
		}
		return valid;
	}
	
	private Map<Combination, Squares> addPiece(Map<Combination, Squares> valid, Piece piece, int pieceNum) {
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
		}
		return newValid;
	}
	
	

}
