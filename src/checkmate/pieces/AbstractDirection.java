package checkmate.pieces;

import java.util.ArrayList;
import java.util.List;

import checkmate.Board;
import checkmate.Position;

public abstract class AbstractDirection extends AbstractPiece {

	final static Position[] DIAGONAL = new Position[]{
		new Position(-1,-1), new Position(-1,1), new Position(1,-1), new Position(1,1)
	};
	
	final static Position[] ORTHOGONAL = new Position[]{
		new Position(-1,0), new Position(0,-1), new Position(0,1), new Position(1,0)
	};

	public AbstractDirection(Board b, Position p) {
		super(b, p);
	}

	abstract Position[] getDirections();

	@Override
	protected Position[] findMoves() {
		Board b = getBoard();
		List<Position> result = new ArrayList<Position>();
		Position start = getPosition();
		for (Position d : getDirections()) {
			int x = d.getX(), y = d.getY();
			for (Position p = start; b.isValid(p); p = p.relative(x, y)) {
				if (!p.equals(start)) result.add(p);
			}
		}
		Position[] moves = new Position[result.size()];
		result.toArray(moves);
		return moves;
	}

}
