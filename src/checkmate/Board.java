package checkmate;

import java.util.HashSet;
import java.util.Set;

import checkmate.pieces.AbstractPiece;
import checkmate.pieces.PieceInterface;

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

	private Set<PieceInterface> pieces = new HashSet<PieceInterface>();
	
	public Set<PieceInterface> getPieces() {
		return pieces;
	}
	
	public boolean canAdd(AbstractPiece piece) {
		for (PieceInterface p : pieces){
			if (p.getPosition().equals(piece.getPosition()) || p.canEat(piece)) return false;
		}
		return true;
	}
	
	public void add(AbstractPiece p) {
		pieces.add( p);
	}
	
	public boolean isValid(Position p) {
		return p.getX()>-1 && p.getY()>-1 && p.getX() < x && p.getY() < y;
	}

}
