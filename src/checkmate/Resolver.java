package checkmate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import checkmate.pieces.Bishop;
import checkmate.pieces.King;
import checkmate.pieces.Knight;
import checkmate.pieces.Pawn;
import checkmate.pieces.Piece;
import checkmate.pieces.Queen;
import checkmate.pieces.Rook;


public class Resolver {
	private Board board;
	
	// creates a new piece using its name
	public Piece newPiece(String type) {
		Position p = new Position(0, 0);
		switch (type) {
		case "bishop":
			return new Bishop(board, p);
		case "king":
			return new King(board, p);
		case "knight":
			return new Knight(board, p);
		case "pawn":
			return new Pawn(board, p);
		case "queen":
			return new Queen(board, p);
		case "rook":
			return new Rook(board, p);
		}
		return null;
	}

	public Resolver(int x, int y) {
		this.setBoard(new Board(x, y));
	}

	// find all the valid positions
	public Set<Position[]> resolve(List<Piece> pieces) {
		Set<Position[]> result = new HashSet<Position[]>();
		int i = 0;
		Position start = new Position(0, 0);
		for (Position pos = start; i >= 0; ) {
			if (pos == null) {
				// no position
				if (i == 0) {
					//no pieces left
					break;
				}
				// reset position for this and the following pieces
				for (int j = i; j<pieces.size(); j++) {
					pieces.get(j).setPosition(start);
				}
				// remove one piece and resume moving the previous
				i--;
				board.pop();
				pos = board.next(pieces.get(i).getPosition());
				continue;
			}
			Piece piece = pieces.get(i);
			piece.setPosition(pos);
			if (!board.isAddSafe(piece)) {
				// cannot add, move to next position
				pos = board.next(pos);
			} else {
				getBoard().push(piece);
				i++;
				// can add, check if all pieces are set
				if (i == pieces.size()) {
					Position[] match = new Position[pieces.size()];
					for (int n=0; n<pieces.size(); n++) {
						match[n] = pieces.get(n).getPosition();
					}
					// add the match to results
					result.add(match);
					// remove last piece and continue with it
					i--;
					piece = getBoard().pop();
					pos =  board.next(piece.getPosition());
				} else {
					// reset position for next piece
					pos = start;
				}
			}
		}
		return result;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}
