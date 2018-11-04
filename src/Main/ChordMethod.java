package Main;

public class ChordMethod {
	double[] points; 
	private static final double DX = 0.00001;
	private double[] coefficients;
	
//	public static double derivative(double x, Derivative func) {
//		return Math.round((func.f(x+DX)-func.f(x))/DX);
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
	public void test() {
		points = new double[]{0,2, 10,1}; // Initializing array of points
        double x1 = 2;
        double x2 = 10;
        double e = 0.001;
        System.out.println(chordMethod(x1, x2, e));
		//System.out.println(polynomial2(10));
	}
	public static void main(String[] args){
		new ChordMethod().test();
	}
}
