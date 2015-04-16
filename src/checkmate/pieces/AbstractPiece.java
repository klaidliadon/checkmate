package checkmate.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import checkmate.Board;
import checkmate.Position;

// The piece in the board
public abstract class AbstractPiece implements Piece {

	private Position position;

	private Board board;

	private Position[] moves;
	
	public AbstractPiece(Board board, Position position) {
		this.board = board;
		this.setPosition(position);
	}

	final public Position getPosition() {
		return position;
	}

	final public void setPosition(Position position) {
		this.position = position;
		List<Position> moves = new ArrayList<Position>();
		for (Position p : findMoves()) {
			if (board.isValid(p)) moves.add(p);
		}
		this.moves = new Position[moves.size()]; 
		moves.toArray(this.moves);
		Arrays.sort(this.moves);
	}

	public Board getBoard() {
		return board;
	}

	public Position[] getMoves() {
		return moves;
	}

	// return the moves that a piece can make
	protected abstract Position[] findMoves();

	// tell if a piece can eat another
	final public boolean canEat(Piece piece) {
		for (Position p : moves) {
			if (p.equals(piece.getPosition())) return true;
		}
		return false;
	}

	@Override
	final public String toString() {
		return String.format("%s%s", getClass().getSimpleName(), position);
	}
}
