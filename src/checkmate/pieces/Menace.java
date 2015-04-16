package checkmate.pieces;

import checkmate.Board;
import checkmate.Position;

public class Menace extends AbstractPiece {

	public Menace(Board b, Position p) {
		super(b, p);
	}

	@Override
	protected Position[] findMoves() {
		return new Position[]{};
	}

}
