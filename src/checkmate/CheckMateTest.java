package checkmate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import checkmate.pieces.AbstractPiece;
import checkmate.pieces.Bishop;
import checkmate.pieces.King;
import checkmate.pieces.Knight;
import checkmate.pieces.Piece;
import checkmate.pieces.Queen;
import checkmate.pieces.Rook;

public class CheckMateTest {

	private Position p(int x, int y) {
		return new Position(x,y);
	}

	private void testMoves(AbstractPiece p, Position[] expected) {
		Position[] result = p.getMoves();
		assertSame(expected.length, result.length);
		Arrays.sort(expected);
		for (int i=0; i< result.length; i++) {
			assertEquals(String.format("Invalid %d element", i), expected[i], result[i]);
		}
	}

	private static class Assertion {
		public Assertion(int first, int second, boolean value) {
			this.first = first; this.second = second; this.value = value;
		}
		int first;
		int second;
		boolean value;
	}

	private void baseTest(Board b, AbstractPiece[] pieces, Position[][] moves, Assertion[] assertions) {
		assertEquals(pieces.length, moves.length);
		for (int i = 0; i < pieces.length; i++) {
			testMoves(pieces[i], moves[i]);
		}
		for (Assertion a : assertions) {
			assertEquals(a.value, pieces[a.first].canEat(pieces[a.second]));
		}
	}

	@Test
	public void testPositionEquals() {
		assertNotEquals(p(-1,-1), p(0,0));
		assertEquals(p(1,1), p(1,1));
		assertNotEquals(p(2,1), p(1,2));
	}

	@Test
	public void testPositionInBoard() {
		Board b = new Board(3, 3);
		assertFalse(b.isValid(p(-1,0)));
		assertTrue(b.isValid(p(0,0)));
		assertTrue(b.isValid(p(1,1)));
		assertTrue(b.isValid(p(2,2)));
		assertFalse(b.isValid(p(3,3)));
		assertFalse(b.isValid(p(6,6)));
	}


	@Test
	public void testPieceBishop() {
		Board b = new Board(4, 4);
		baseTest(b, new AbstractPiece[]{
				new Bishop(b, p(0,0)), 
				new Bishop(b, p(1,1)), 
				new Bishop(b, p(1,2)),
		}, new Position[][]{ 
				new Position[]{p(1,1), p(2,2), p(3,3)},
				new Position[]{p(0,0), p(0,2), p(2,0), p(2,2),  p(3,3)},
				new Position[]{p(0,1), p(0,3), p(2,1), p(2,3),  p(3,0)},
		}, new Assertion[]{
				new Assertion(0,1,true),new Assertion(1,2,false),new Assertion(0,2,false),
		});
	}

	@Test
	public void testPieceKing() {
		Board b = new Board(3, 3);
		baseTest(b, new AbstractPiece[]{
				new King(b, p(0,0)), 
				new King(b, p(1,1)), 
				new King(b, p(2,2)),
		}, new Position[][]{ 
				new Position[]{p(0,1), p(1,0),  p(1,1)},
				new Position[]{p(0,0), p(0,1),  p(0,2), p(1,0), p(1,2),  p(2,0), p(2,1), p(2,2)},
				new Position[]{p(2,1), p(1,2),  p(1,1)},
		}, new Assertion[]{
				new Assertion(0,1,true),new Assertion(1,2,true),new Assertion(0,2,false),
		});
	}

	@Test
	public void testPieceKnight() {
		Board b = new Board(5, 5);
		baseTest(b, new AbstractPiece[]{
		new Knight(b, p(0,0)),
		new Knight(b, p(2,1)),
		new Knight(b, p(2,2)),
		}, new Position[][]{
		new Position[]{p(1,2), p(2,1)},
		new Position[]{p(0,0), p(0,2),  p(1,3), p(3,3), p(4,0), p(4,2),},
		new Position[]{p(0,1), p(0,3), p(1,0), p(1,4), p(3,0), p(3,4), p(4,1), p(4,3)},
		}, new Assertion[]{
			new Assertion(0,1,true),new Assertion(1,2,false),new Assertion(0,2,false),
		});
	}

	@Test
	public void testPieceQueen() {
		Board b = new Board(3, 3);
		baseTest(b, new AbstractPiece[]{
		new Queen(b, p(0,0)),
		new Queen(b, p(0,2)),
		new Queen(b, p(2,1)),
		}, new Position[][]{
		new Position[]{p(0,1), p(0,2), p(1,0), p(1,1), p(2,0), p(2,2)},
		new Position[]{p(0,0), p(0,1),  p(1,1), p(1,2), p(2,0), p(2,2)},
		new Position[]{p(0,1), p(1,0), p(1,1), p(1,2), p(2,0), p(2,2)},
		}, new Assertion[]{
			new Assertion(0,1,true),new Assertion(1,2,false),new Assertion(0,2,false),
		});
	}

	@Test
	public void testPieceRook() {
		Board b = new Board(3, 3);
		baseTest(b, new AbstractPiece[]{
		new Rook(b, p(0,0)),
		new Rook(b, p(0,2)),
		new Rook(b, p(2,1)),
		}, new Position[][]{
		new Position[]{p(0,1), p(0,2), p(1,0), p(2,0)},
		new Position[]{p(0,0), p(0,1), p(1,2), p(2,2)},
		new Position[]{p(0,1), p(1,1), p(2,0), p(2,2)},
		}, new Assertion[]{
			new Assertion(0,1,true),new Assertion(1,2,false),new Assertion(0,2,false),
		});
	}

	@Test
	public void testResolver() {
		Resolver r = new Resolver(3, 3);
		List<Piece> l = new ArrayList<Piece>();
		Position start = p(0,0);
		l.add(new King(r.getBoard(), start));
		l.add(new King(r.getBoard(), start));
		r.setPieces(l);
		System.out.println("[King] [King]");
		Set<Position[]> result = r.resolve();
		for (Position[] match : result) {
			for (Position s : match) {
				System.out.print(s+" ");
			}
			System.out.println();
		}
	}

}
