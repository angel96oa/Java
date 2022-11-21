
public class ParActionValue {
	
	public Action a;
	public double v;

	public ParActionValue() {
		
	}
	
	public ParActionValue(Action a, double v) {
		this.a = a;
		this.v = v;
	}
	
	public ParActionValue(ParActionValue a) {
		this.a = a.a;
		this.v = a.v;
	}

}
