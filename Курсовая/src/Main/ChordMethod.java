package Main;

import java.text.DecimalFormat;

import Jama.Matrix;

public class ChordMethod {
	double[] points; 
//	private static final double DX = 0.00001;
//	private double[] coefficients;
	private Matrix ans;
	
//	public static double derivative(double x, Derivative func) {
//		return Math.round((func.f(x+DX)-func.f(x))/DX);
//	}
	// Finds Lagrange's Polynomial coefficients
//	public void getCoefficients(double[] points) {
//		coefficients = new double[points.length/2];
//		for (int i=0; i<points.length; i+=2) {
//			double der = derivative(points[i], x->{
//				//System.out.println(x);
//				double w=1;
//				for (int j=0; j<points.length; j+=2) {
//					w*=(x-points[j]); 
//				}
//				//System.out.println(w);
//				return w; // Returns auxiliary polynomial
//			});
//			coefficients[i/2]=der;
//		}
//	}
	   public double chordMethod (double x_prev, double x_curr, double e) {
	        double x_next = 0;
	        double tmp;
	        do{
	            tmp = x_next;
	            x_next = x_curr - polynomial(x_curr) * (x_prev - x_curr) / (polynomial(x_prev) - polynomial(x_curr));
	            x_prev = x_curr;
	            x_curr = tmp;
	        } while (Math.abs(x_next - x_curr) > e);
	        return x_next;
	    }
	    
	    public static double f (double x){
	        return Math.pow(x, 3) - 18 * x - 83;
	    }
	
	public double polynomial(double arg) {
		double polynomial=0;		
		
		for (int i=0; i<points.length; i+=2) {
			double basis_polynomial = 1;
			for (int j=0; j<points.length; j+=2) {
				if(i!=j) {
					basis_polynomial *= (arg-points[j]) / (points[i]-points[j]);
				}
			}
			polynomial += points[i+1]*basis_polynomial;
		}
		return polynomial;
	}
	public String getFormula() {
        double[][] lhsArray = new double[points.length/2][points.length/2];
        double[] rhsArray = new double[points.length/2];
        for (int i=0;i<points.length/2;i++) { // Creating Matrix of Xs
        	for (int j=0; j<points.length/2; j++) {
        		lhsArray[i][j]=Math.pow(points[i*2],j);
            	//System.out.print(Math.pow(points[i*2],j)+" ");
        	}
        	rhsArray[i]=points[i*2+1]; // Creating Matrix of Ys
        	//System.out.println(points[i*2+1]);
        }
        Matrix lhs = new Matrix(lhsArray);
        Matrix rhs = new Matrix(rhsArray, points.length/2);
        ans = lhs.solve(rhs);
        DecimalFormat fmt = new DecimalFormat("+ #,##0.0;- #");
        String str=String.format("%.2f", ans.get(0, 0))+" ";
        for (int i=1; i<ans.getRowDimension(); i++) {
        	str+=fmt.format(ans.get(i,0))+"x^"+i+" ";
        }
        return str;
	}
	public void test() {
		//points = new double[]{0,1, 1,3, 3,27, 4,81}; // Initializing array of points
		points = new double[]{-3.5,-550, -3.25,-50, 3,27}; // Initializing array of points

        System.out.println(getFormula()); // Printing formula
        System.out.printf("%.0f",chordMethod(2, 3, 0.001)); // Printing root
	}
	public static void main(String[] args){
		new ChordMethod().test();
	}
	
}
