package checkmate.pieces;

import checkmate.Board;
import checkmate.Position;

public class Rook extends AbstractDirection {

	public Rook(Board b, Position p) {
		super(b, p);
	}

	@Override
	Position[] getDirections() {
		return ORTHOGONAL;
	}

}
