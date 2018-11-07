package Function.Model;

public class ArrayFFunction extends AbstractFFunction {

	private double[] coefs;
	public ArrayFFunction(double... coefs) {
		this.coefs = coefs;
	}
	@Override
	public double getCoef(int i) {
		return coefs[i];
	}

	@Override
	public void setCoef(int i, double coef) {
		coefs[i]=coef;
	}

	@Override
	public int getCoefCount() {
		return coefs.length;
	}
	
	@Override
	public void addCoef(double coef) {
		throw new UnsupportedOperationException();
	}
	
	public static void main(String[] args) {
		// y=x
		System.out.println(new ArrayFFunction(1,0).test("a", "f", -2, 2, 1));
		// y=x^2
		System.out.println(new ArrayFFunction(1,0,0).test("a", "f", -2, 2, 1));
		// y=x^2+1
		System.out.println(new ArrayFFunction(1,0,1).test("a", "f", -2, 2, 1));
	}
}
