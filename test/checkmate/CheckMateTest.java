package checkmate;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert; 
import org.junit.Test;


public class CheckMateTest {
	
	private Position p(int x, int y) {
		return new Position(x, y);
	}
	
	private void menaceTest(Piece piece, Position pos, Map<Position, Boolean> menaced) {
		int x = pos.getX();
		int y = pos.getY();
		for (Entry<Position, Boolean> entry: menaced.entrySet()) {
			Position m = entry.getKey();
			Assert.assertEquals(String.format("Testing %s with %s", pos, m), entry.getValue(), piece.isMenaced(m.getX(), m.getY(), x, y));
		}
	}
	
	@Test
	public void testPieceBishop(){
		Piece piece = Piece.BISHOP;
		Position pos = p(0,0);
		Map<Position, Boolean> m = new HashMap<Position, Boolean>();
		m.put(p(0,1), false);
		m.put(p(0,2), false);
		m.put(p(1,0), false);
		m.put(p(1,1), true);
		menaceTest(piece, pos, m);
	}
	
	@Test
	public void testPieceKing(){
		Piece piece = Piece.KING;
		Position pos = p(1,1);
		Map<Position, Boolean> m = new HashMap<Position, Boolean>();
		m.put(p(0,1), true);
		m.put(p(0,2), true);
		m.put(p(1,0), true);
		m.put(p(1,1), true);
		m.put(p(1,4), false);
		m.put(p(3,2), false);
		menaceTest(piece, pos, m);
	}
	
	@Test
	public void testPieceKnight(){
		Piece piece = Piece.KNIGHT;
		Position pos = p(2,2);
		Map<Position, Boolean> m = new HashMap<Position, Boolean>();
		m.put(p(0,1), true);
		m.put(p(0,2), false);
		m.put(p(1,0), true);
		m.put(p(1,1), false);
		m.put(p(1,4), true);
		m.put(p(3,2), false);
		menaceTest(piece, pos, m);
	}
	
	@Test
	public void testPieceQueen(){
		Piece piece = Piece.QUEEN;
		Position pos = p(2,2);
		Map<Position, Boolean> m = new HashMap<Position, Boolean>();
		m.put(p(0,1), false);
		m.put(p(0,2), true);
		m.put(p(1,0), false);
		m.put(p(1,1), true);
		m.put(p(1,4), false);
		m.put(p(3,2), true);
		menaceTest(piece, pos, m);
	}
	
	@Test
	public void testPieceRook(){
		Piece piece = Piece.ROOK;
		Position pos = p(2,2);
		Map<Position, Boolean> m = new HashMap<Position, Boolean>();
		m.put(p(0,1), false);
		m.put(p(0,2), true);
		m.put(p(1,0), false);
		m.put(p(1,1), false);
		m.put(p(1,4), false);
		m.put(p(3,2), true);
		menaceTest(piece, pos, m);
	}
}
