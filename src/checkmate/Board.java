package checkmate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import checkmate.pieces.Piece;

// The actual board
public class Board {

	private int x, y;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Board(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private Stack<Piece> pieces = new Stack<Piece>();
	// cache of occupied positions
	private Map<Position, List<Piece>> cache = new HashMap<Position, List<Piece>>();
	
	public Collection<Piece> getPieces() {
		return pieces;
	}
	
	// check if a spot is free and not menaced
	public boolean isAddSafe(Piece piece) {
		Position pos = piece.getPosition();
		List<Piece> cacheEntry = cache.get(pos);
		if (cacheEntry != null && !cacheEntry.isEmpty()){
			System.out.println(String.format("Cannot add %s: menaced by %s", piece, cacheEntry.get(0)));
			return false;
		}
		for (Piece p : pieces){
			if (piece.canEat(p)) {
				System.out.println(String.format("Cannot add %s: menacing by %s", piece, p));
				return false;
			}
		}
		return true;
	}
	
	private void addToCache(Piece p, Position pos) throws Exception {
		if (pos == null) {
			pos = p.getPosition();
			System.out.println(String.format("No position specificed, using %s", p));
		}
		List<Piece> list = cache.get(pos);
		if (list == null) {
			list = new ArrayList<Piece>();
			cache.put(pos, list);
		}
		if (list.isEmpty() || p.getPosition() != pos) {
			System.out.println(String.format("Adding menace in %s", pos));
			list.add(p);
		} else {
			throw new Exception(String.format("Cannot put %s in %s, already menaced by %s", p, p.getPosition(), list.get(0)));
		}
	}
	
	private void removeFromCache(Position pos) throws Exception {
		List<Piece> list = cache.get(pos);
		if (list == null || list.isEmpty()) {
			throw new Exception(String.format("Cannot remove from %s, position is empty", pos));
		}
		list.get(list.size()-1);
		System.out.println(String.format("Removing menace in %s, position menace count is %d", pos, list.size()));
	}
	
	// add a piece to the board
	public void push(Piece p) throws Exception {
		System.out.println(String.format("Adding piece %d %s in %s", pieces.size()+1, p.getClass().getSimpleName(), p.getPosition()));
		addToCache(p, null);
		pieces.push(p);
		for (Position pos : p.getMoves()) {
			System.out.println(String.format("Adding %s manaced by %s", pos, p));
			addToCache(p, pos);
		}
	}
	
	// remove last placed piece
	public Piece pop() throws Exception {
		Piece p = pieces.pop();
		System.out.println(String.format("Removing %s", p));
		removeFromCache(p.getPosition());
		for (Position pos : p.getMoves()) {
			System.out.println(String.format("Removing piece %d %s manaced by %s", pieces.size(), pos, p));
			removeFromCache(pos);
		}
		return p;
	}
	
	// check if the square is in the board
	public boolean isValid(Position p) {
		return p.getX()>-1 && p.getY()>-1 && p.getX() < x && p.getY() < y;
	}
	
	// move to the next square in the check
	public Position next(Position p) {
		Position result = p.relative(1, 0);
		if (isValid(result)) {
			return result;
		}
		result = p.relative(-p.getX(), 1);
		if (isValid(result)) {
			return result;
		}
		return null;	
	}

}
