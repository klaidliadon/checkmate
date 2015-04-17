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
	public Set<Position[]> resolve() throws Exception {
		Set<Position[]> result = new HashSet<Position[]>();
		Set<String> resultString = new HashSet<String>();
		int i = 0;
		int counter = 1;
		Position start = new Position(0, 0);
		Piece first = pieces.get(0);
		for (Position pos = start; i >= 0; counter++) {
			if (pos == null) {
				// no position
				if (i == 0) {
					if (board.next(first.getPosition()) == null) {
						// first piece finished
						System.out.println(String.format("exit: %s", first));
						break;
					}
					board.reset();
				} else {
					// remove one piece and resume moving the previous
					board.pop();
					i--;
					// reset position for this and the following pieces
					for (int j = i+1; j<getPieces().size(); j++) {
						getPieces().get(j).setPosition(start);
					}
					pos = board.next(getPieces().get(i).getPosition());
					continue;
				}
			}
			if (Board.DEBUG) {
				System.out.print(String.format("Status [%d results] (iteration %d) - Current: ",result.size(), counter, i));
				for (int k = 0; k<=i-1; k++) {
					System.out.print(pieces.get(k)+" ");
				}
				System.out.println();
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
					String sMatch = matchString(match);
					boolean newMatch = true;
					if (!newMatch) {
						for (String s: resultString) {
							if (!s.equals(sMatch)) continue;
							newMatch = true;
							break;
						}
					}
					if (newMatch) {
						result.add(match);
						resultString.add(sMatch);
						System.out.print(String.format("Match (%d): ", counter));
						for (int k = 0; k<=i-1; k++) {
							System.out.print(pieces.get(k)+" ");
						}
						System.out.println();
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
		System.out.println(String.format("Final Status (i:%d, p:%s)", counter, i));
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
	
	private String matchString(Position[] p) {
		String[] r = new String[p.length];
		for (int i = 0; i<p.length; i++) {
			r[i] = pieces.get(i).getClass().getSimpleName()+p[i].toString();
		}
		Arrays.sort(r);
		String result = "";
		for (String s : r) {
			result += s;
		}
		return result;
	}
}