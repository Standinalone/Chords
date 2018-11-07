package Function.Model;

public abstract class AbstractGFunction implements ExtendedFunction {
	public abstract double getX(int i);
	public abstract double getY(int i);
	public abstract void setPoint(double x, double y, int i);
	public abstract void addPoint(double x, double y);
	public abstract int getPointsCount();
	
	@Override
	public double applyAsDouble(double x) {
		double polynomial=0;		
		for (int i=0; i<getPointsCount(); i++) {
			double basis_polynomial = 1;
			for (int j=0; j<getPointsCount(); j++) {
				if(i!=j) {
					basis_polynomial *= (x-getX(j)) / (getX(i)-getX(j));
				}
			}
			polynomial += getY(i)*basis_polynomial;
		}
		return polynomial;
	}

}
