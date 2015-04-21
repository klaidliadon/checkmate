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
	

}
