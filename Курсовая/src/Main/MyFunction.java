package Main;

import Jama.Matrix;

public class MyFunction{
	private double[] points;
	private double[] coefs;
//	private Point[] points; 
//	private Matrix ans;
//	public void setPoints (Point[] points) { // Setter to set points for the interpolation
//		this.points = points;
//	}
//	public Point[] getPoints() {
//		return points;
//	}
//	public Matrix getAnswers() { // Getter to get polynomial coefficients
//		return ans;
//	}

//	    
//	

//	public void Vandermonde(double[] X, double[] Y, int degree) {
//		degree = X.length;
//	}
//	public String getFormula() {
//		int martrixSize = getPoints().length/2;
//        double[][] lhsArray = new double[martrixSize][martrixSize];
//        double[] rhsArray = new double[martrixSize];
//        for (int i=0; i<martrixSize;i++) { 
//        	for (int j=0; j<martrixSize; j++) {
//        		lhsArray[i][j]=Math.pow(points[i*2],j); // Creating Matrix of Xs (left-hand side)
//        	}
//        	rhsArray[i]=points[i*2+1]; // Creating Matrix of Ys (rifht-hand side)
//        	//System.out.println(points[i*2+1]);
//        }
//        Matrix lhs = new Matrix(lhsArray);
//        Matrix rhs = new Matrix(rhsArray, points.length/2);
//        ans = lhs.solve(rhs);
//        DecimalFormat fmt = new DecimalFormat("+ #,##0.0;- #");
//        String str=String.format("%.2f", ans.get(0, 0))+" ";
//        for (int i=1; i<ans.getRowDimension(); i++) {
//        	str+=fmt.format(ans.get(i,0))+"x^"+i+" ";
//        }
//        return str;
//	}

// Finding the root of function f(x) - g(x) = 0	
	public double chordMethod (double xPrev, double xCurr, double eps) {
		double xNext=0, tmp = 0;
		do{
			tmp = xNext;
			xNext = xCurr - (f(xCurr)-g(xCurr))*(xPrev - xCurr) / ((f(xPrev)-g(xPrev)) - (f(xCurr)-g(xCurr)));
			xPrev=xCurr;
			xCurr = tmp;
		}while (Math.abs(xNext-xCurr)>eps) ;
		return(xNext);
	}
	
// Returning the value of Lagrange's polynomial at X
	public double g(double x) {
		double polynomial=0;		
		
		for (int i=0; i<points.length; i+=2) {
			double basis_polynomial = 1;
			for (int j=0; j<points.length; j+=2) {
				if(i!=j) {
					basis_polynomial *= (x-points[j]) / (points[i]-points[j]);
				}
			}
			polynomial += points[i+1]*basis_polynomial;
		}
		return polynomial;
	}

// Returning the value of f(x)
	public double f(double x) {
		double result=0;
		for (int i=0; i<coefs.length-1; i++) {
			result += coefs[i]*Math.pow(x, coefs.length-i-1);
		}
		result+=coefs[coefs.length-1];
		return result;
	}
	public void test() {
		final int n=3;
		coefs = new double[]{1,0,0};	
		System.out.println(f(1));
// Initializing array of points for polynomial g(x)		
		points = new double[]{-5,-5, 3,3}; 
		System.out.println(g(0));
		System.out.println(chordMethod(-2,2,0.0001));
		
	}
	public static void main(String[] args){
		new MyFunction().test();
	}
}
