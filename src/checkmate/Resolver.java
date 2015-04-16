package checkmate;

import java.util.Arrays;
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
	private List<Piece> pieces;
	
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
	public Set<Position[]> resolve() {
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
				for (int j = i; j<getPieces().size(); j++) {
					getPieces().get(j).setPosition(start);
				}
				// remove one piece and resume moving the previous
				i--;
				board.pop();
				pos = board.next(getPieces().get(i).getPosition());
				continue;
			}
			Piece piece = getPieces().get(i);
			piece.setPosition(pos);
			if (!board.isAddSafe(piece)) {
				// cannot add, move to next position
				pos = board.next(pos);
			} else {
				getBoard().push(piece);
				i++;
				// can add, check if all pieces are set
				if (i == getPieces().size()) {
					Position[] match = new Position[getPieces().size()];
					for (int n=0; n<getPieces().size(); n++) {
						match[n] = getPieces().get(n).getPosition();
					}
					String[] m1 = matchString(match);
					boolean newMatch = result.size() == 0;
					for (Position[] p : result) {
						String[] m2 = matchString(p);
						for (int j = 0; j<m1.length; j++) {
							// this result is unique
							if (!m1[j].equals(m2[j])) newMatch = true;
						}
					}
					if (newMatch) {
						result.add(match);
					}
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

	public List<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}
	
	private String[] matchString(Position[] p) {
		String[] r = new String[p.length];
		for (int i = 0; i<p.length; i++) {
			r[i] = pieces.get(i).getClass().getSimpleName()+p[i].toString();
		}
		Arrays.sort(r);
		return r;
	}
}