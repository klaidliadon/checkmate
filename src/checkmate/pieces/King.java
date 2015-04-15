package checkmate.pieces;

import checkmate.Board;
import checkmate.Position;

public class King extends AbstractPiece {

	public King(Board b, Position p) {
		super(b, p);
	}

	@Override
	protected Position[] findMoves() {
		Position p = getPosition();
		return new Position[] {
				p.relative(-1,  1),	p.relative( 0,  1),	p.relative( 1,  1),
				p.relative(-1,  0),                   	p.relative( 1,  0),
				p.relative(-1, -1),	p.relative( 0, -1),	p.relative( 1, -1),	
		};
	}

}
