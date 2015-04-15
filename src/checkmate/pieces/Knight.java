package checkmate.pieces;

import checkmate.Board;
import checkmate.Position;

public class Knight extends AbstractPiece {

	public Knight(Board b, Position p) {
		super(b, p);
	}

	@Override
	protected Position[] findMoves() {
		Position p = getPosition();
		return new Position[] {
				p.relative(-2,  -1), p.relative( -2,  +1),
				p.relative(-1,  -2), p.relative( -1,  +2),
				p.relative(+1,  -2), p.relative( +1,  +2),
				p.relative(+2,  -1), p.relative( +2,  +1),	
		};
	}

}
