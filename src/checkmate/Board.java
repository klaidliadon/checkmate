package checkmate;

import java.util.HashMap;
import java.util.Map;

import checkmate.pieces.AbstractPiece;

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

	private Map<Position, AbstractPiece> pieces = new HashMap<Position, AbstractPiece>();
	
	public boolean canAdd(AbstractPiece piece) {
		if (pieces.containsKey(piece.getPosition())) return false;
		for (Position p : pieces.keySet()){
			if (pieces.get(p).canEat(piece)) return false;
		}
		return true;
	}
	
	public void add(AbstractPiece p) {
		pieces.put(p.getPosition(), p);
	}
	
	public boolean isValid(Position p) {
		return p.getX()>-1 && p.getY()>-1 && p.getX() < x && p.getY() < y;
	}

}
