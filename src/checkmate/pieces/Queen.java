package checkmate.pieces;

import java.util.Arrays;

import checkmate.Board;
import checkmate.Position;

public class Queen extends AbstractDirection {
	
	static private Position[] direction;
	
	static {
		direction = new Position[ORTHOGONAL.length+DIAGONAL.length];
		for (int i = 0; i<ORTHOGONAL.length; i++) {
			direction[i] = ORTHOGONAL[i];
		}
		for (int i = ORTHOGONAL.length; i<direction.length; i++) {
			direction[i] = DIAGONAL[i-ORTHOGONAL.length];
		}
		Arrays.sort(direction);
	}

	public Queen(Board b, Position p) {
		super(b, p);
	}

	@Override
	Position[] getDirections() {
		return direction;
	}

}
