package Function.Model;

import java.util.Arrays;

public class ArrayGFunction extends AbstractGFunction{
	private double[] points;
	
	public ArrayGFunction(double... points){
		if (points.length%2>0) throw new RuntimeException("amount of Xs should be equal to amount of Ys");
		this.points = points;
	}
	@Override
	public double getX(int i) {
		return points[i*2];
	}

	@Override
	public double getY(int i) {
		return points[i*2+1];
	}

	@Override
	public void setPoint(double x, double y, int i) {
		points[i*2]=x;
		points[i*2+1]=y;
	}

	@Override
	public void addPoint(double x, double y) {
//		throw new UnsupportedOperationException();
		if (points==null) points = new double[0];
		points = Arrays.copyOf(points, points.length+2);
		points[points.length-2]=x;
		points[points.length-1]=y;
		
	}

	@Override
	public int getPointsCount() {
		return points.length/2;
	}

	public static void main(String[] args) {
		// Normal work
		System.out.println(new ArrayGFunction(-1,2, 1,2, 2,5).getFormula()); // g(x) = x^2+1
		System.out.println(new ArrayGFunction(-5,-5, 3,3).test("a", "f", -2, 2, 1));
		// Entering an odd amount of digits
		System.out.println(new ArrayGFunction(1,0,3).getFormula());
		System.out.println(new ArrayGFunction(1,0,3).test("a", "f", -2, 2, 1));
	}
}
