package checkmate;


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
			int a,b;
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
	
	public Combination[] getAlternative(){
		Combination[] result = new Combination[]{
				new Combination(w,h),
				new Combination(w,h),
				new Combination(w,h),
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

}
