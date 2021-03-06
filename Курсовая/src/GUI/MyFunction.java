package GUI;

import Jama.Matrix;

public class MyFunction{
	private double[] points;
	private double[] coefs;
//	public void Vandermonde(double[] X, double[] Y, int degree) {
//		degree = X.length;
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
