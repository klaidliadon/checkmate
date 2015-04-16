package checkmate;

import java.util.Collection;
import java.util.Stack;

import checkmate.pieces.Piece;

// The actual board
public class Board {

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
	}

	private Stack<Piece> pieces = new Stack<Piece>();
	
	public Collection<Piece> getPieces() {
		return pieces;
	}
	
	// check if a spot is free and not menaced
	public boolean isAddSafe(Piece piece) {
		for (Piece p : pieces){
			if (piece.getPosition().equals(p.getPosition()) || piece.canEat(p) || p.canEat(piece)) return false;
		}
		return true;
	}
	
	// add a piece to the board
	public void push(Piece p) {
		pieces.push(p);
	}
	
	// remove last placed piece
	public Piece pop() {
		Piece p = pieces.pop();
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
