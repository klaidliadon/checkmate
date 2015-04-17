package checkmate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import checkmate.pieces.Piece;

// The actual board
public class Board {

	public static final boolean DEBUG = false;
	
	private int x, y;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Board(int x, int y) {
		this.x = x;
		this.y = y;
		reset();
	}
	
	public void reset() {
		pieces = new Stack<Piece>();
	} 

	private Stack<Piece> pieces;
	
	private static Map<String, Boolean> validityCache = new HashMap<String, Boolean>();
	
	public Collection<Piece> getPieces() {
		return pieces;
	}
	
	// check if a spot is free and not menaced
	public boolean isAddSafe(Piece piece) {
		Position pos = piece.getPosition();
		boolean menacing = false;
		for (Piece p : pieces) {
			// any other piece in the spot
			if (p.getPosition().equals(pos)) {
				if (DEBUG) System.out.println(String.format("Cannot add %s: occupied by %s", piece, p));
				return false;
			}
			// is it in cache
			String s = p.toString()+piece.toString();
			if (validityCache.containsKey(s)) {
				boolean result = validityCache.get(s);
				if (DEBUG) System.out.println(String.format("load cache %s "+result, s));
				if (result == false) return result;
			}
			if (piece.canEat(p) || p.canEat(piece)) {
				if (DEBUG) System.out.println(String.format("Cannot add %s: menacing by %s", piece, p));
				menacing = true;
			}
			String s1 = p.toString()+piece.toString();
			String s2 = p.toString()+piece.toString();
			validityCache.put(s1, !menacing);
			validityCache.put(s2, !menacing);
			if (DEBUG) System.out.println(String.format("save cache %s %s "+!menacing, s1, s2));
		}
		return !menacing;
	}
	
	// add a piece to the board
	public void push(Piece p) throws Exception {
		if (DEBUG) System.out.println(String.format("Adding piece %d %s in %s", pieces.size()+1, p.getClass().getSimpleName(), p.getPosition()));
		pieces.push(p);
	}
	
	// remove last placed piece
	public Piece pop() throws Exception {
		Piece p = pieces.pop();
		if (DEBUG) System.out.println(String.format("Removing %s", p));
		return p;
	}
	
	// check if the square is in the board
	public boolean isValid(Position p) {
		return p.getX()>-1 && p.getY()>-1 && p.getX() < x && p.getY() < y;
	}
	
	// move to the next square in the check
	public Position next(Position p) {
		Position result = p.relative(1, 0);
		if (isValid(result)) {
			return result;
		}
		result = p.relative(-p.getX(), 1);
		if (isValid(result)) {
			return result;
		}
		return null;	
	}

}
