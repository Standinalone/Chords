package Function.Model;

import java.text.DecimalFormat;

import Jama.Matrix;

public abstract class AbstractGFunction implements ExtendedFunction {
	public abstract double getX(int i);
	public abstract double getY(int i);
	public abstract void setPoint(double x, double y, int i);
	public abstract void addPoint(double x, double y);
	public abstract int getPointsCount();
	public abstract void removePoint(int row);
	
	public String getFormula() {
		int martrixSize = getPointsCount();
		double[][] lhsArray = new double[martrixSize][martrixSize];
	    double[] rhsArray = new double[martrixSize];
	    for (int i=0; i<martrixSize;i++) { 
	    	for (int j=0; j<martrixSize; j++) {
	    		// Creating Matrix of Xs (left-hand side)
	    		lhsArray[i][j]=Math.pow(getX(i),j); 
	    	}
	    	// Creating Matrix of Ys (rifht-hand side)
	    	rhsArray[i]=getY(i); 
	    }
	    // Resolving the linear system with the JAMA library (obtaining coefficients)
	    Matrix lhs = new Matrix(lhsArray);
	    Matrix rhs = new Matrix(rhsArray, martrixSize);
	    Matrix ans = lhs.solve(rhs);
	    // Getting rid of the sign preceding the first coefficient
	    String str=new DecimalFormat("0.00").format(ans.get(ans.getRowDimension()-1, 0))+"x^"+(ans.getRowDimension()-1);
	    DecimalFormat fmt = new DecimalFormat(" + #,##0.00; - #");
	    for (int i=ans.getRowDimension()-2; i>0; i--) {
	    	str+=fmt.format(ans.get(i,0))+"x^"+i;
	    }
	    str+=fmt.format(ans.get(0, 0));
	    return str;
	}
	
	@Override
	public double applyAsDouble(double x) {
		// Finding Lagrange's polynomial
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