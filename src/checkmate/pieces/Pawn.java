package checkmate.pieces;

import checkmate.Board;
import checkmate.Position;

public class Pawn extends AbstractPiece {

	public Pawn(Board b, Position p) {
		super(b, p);
	}

	@Override
	protected Position[] findMoves() {
		Position p = getPosition();
		return new Position[] {
				p.relative(0,-1), 
				p.relative(0,+1),
		};
	}

}
