package checkmate;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Combination extends Squares {

	private static final long serialVersionUID = 3378817051333279676L;

	public Combination(int w, int h) {
		super(w, h);
	}

	// hashcode based on toString for include piece order
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public Combination getAlt(boolean x, boolean y, boolean invert) {
		Combination c = new Combination(w, h);
		for (int p : this) {
			int a, b;
			if (invert) {
				a = x(p);
				b = y(p);
			} else {
				a = x(p);
				b = y(p);
			}
			if (x) {
				a = invertX(a);
			}
			if (y) {
				b = invertY(b);
			}
			c.add(xy(a, b));
		}
		return c;
	}

	public Combination[] getAlternative() {
		Combination[] result = new Combination[] {
				new Combination(w, h),
				new Combination(w, h), 
				new Combination(w, h), 
		};
		for (int p : this) {
			int x = x(p);
			int y = y(p);
			int ix = invertX(x);
			int iy = invertY(y);
			result[0].add(xy(x, iy));
			result[1].add(xy(ix, y));
			result[2].add(xy(ix, iy));
		}
		return result;
	}

	public void verify(List<Piece> list) throws Exception {
		if (size() != list.size()) {
			throw new Exception(String.format("Invalid piece list (size %d, expected %d)", size(), list.size()));
		}
		Map<Integer, Piece> c1 = new LinkedHashMap<Integer, Piece>();
		Map<Integer, Piece> c2 = new LinkedHashMap<Integer, Piece>();
		Iterator<Piece> pieces = list.iterator();
		for (Integer point : this) {
			Piece piece = pieces.next();
			c1.put(point, piece);
			c2.put(point, piece);
		}
		for (Entry<Integer, Piece> entry : c1.entrySet()) {
			Piece piece = entry.getValue();
			Integer p = entry.getKey();
			int px = x(p);
			int py = y(p);
			for (int i : c2.keySet()) {
				if (p == i) continue;
				int ix = x(i);
				int iy = y(i);
				if (piece.isMenaced(ix, iy, px, py))
					throw new Exception(String.format(
							"Combination %s: [%d,%d] menace [%d, %d]", this,
							px, py, ix, iy));
			}
		}
	}

}
