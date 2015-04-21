package checkmate;

import java.io.StringWriter;
import java.util.LinkedHashSet;

public class Squares extends LinkedHashSet<Integer> {

	private static final long serialVersionUID = 3378817051333279676L;
	
	public final int w;
	public final int h;
	
	public Squares(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public Squares menace(Squares menaced) {
		Squares newSquares = new Squares(w, h);
		for (Integer i : this) {
			if (!menaced.contains(i)) newSquares.add(i);
		}
		return newSquares;
	}

	public int x(int p) {
		return p%w;
	}
	
	public int y(int p) {
		return (int) Math.floor(p/w);
	}
	
	public int xy(int x, int y) {
		return x+w*y;
	}
	
	public int invertX(int x) {
		return (w-1)-x;
	}
	
	public int invertY(int y) {
		return (h-1)-y;
	}
	
	@Override
	public String toString() {
		return getString();
	}
	
	public String getString(Integer... p) {
		StringWriter s = new StringWriter();
		if (p.length == 0) {
			p = new Integer[this.size()];
			p = this.toArray(p);
		}
		for (Integer i : p){
			s.write(String.format("[%d,%d]", this.x(i), this.y(i)));
		}
		return s.toString();
	}
}
