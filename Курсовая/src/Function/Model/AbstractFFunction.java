package Function.Model;

public abstract class AbstractFFunction implements ExtendedFunction {
	public abstract double getCoef(int i);
	public abstract void setCoef(int i, double coef);
	public abstract int getCoefCount();
	public abstract void addCoef(double coef);
	
	@Override
	public double applyAsDouble(double x) {
		double result=0;
		for (int i=0; i<getCoefCount()-1; i++) {
			result += getCoef(i)*Math.pow(x, getCoefCount()-i-1);
		}
		result+=getCoef(getCoefCount()-1);
		return result;
	}

}
