package checkmate;

// Position on the board
public class Position implements Comparable<Position> {
	
	private int x,y;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position relative(int x, int y) {
		return new Position(this.x + x, this.y + y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position){
			Position p = (Position) obj;
			return p.x == this.x && p.y == this.y;
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return String.format("[%d,%d]", x,y);
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public int compareTo(Position o) {
		if (x != o.x) {
			return x-o.x;
		} else {
			return y-o.y;
		}
	}

}
