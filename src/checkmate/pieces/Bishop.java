package checkmate.pieces;

import checkmate.Board;
import checkmate.Position;

public class Bishop extends AbstractDirection {

	public Bishop(Board b, Position p) {
		super(b, p);
	}

	@Override
	Position[] getDirections() {
		return DIAGONAL;
	}

}
