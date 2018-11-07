package Function.Model.xml;

import Function.Model.AbstractGFunction;
import Function.Model.ArrayGFunction;
import Function.Model.xml.EquationData.Points.XYCoef;

public class XMLGFunction extends AbstractGFunction{
	private EquationData.Points points;
	
	public XMLGFunction(EquationData data){
		if (data.getPoints()==null) {
			points = new EquationData.Points();
		}
		// getXYCoef return the list of points
		//if ((data.points.getXYCoef().size()%2>0)) throw new RuntimeException("amount of Xs should be equal to amount of Ys");
		this.points = data.points;
	}
	@Override
	public double getX(int i) {
		return points.getXYCoef().get(i).getX();
	}

	@Override
	public double getY(int i) {
		return points.getXYCoef().get(i).getY();
	}

	@Override
	public void setPoint(double x, double y, int i) {
		points.getXYCoef().get(i).setX(x);
		points.getXYCoef().get(i).setX(y);
	}

	@Override
	public void addPoint(double x, double y) {
		XYCoef tmp = new XYCoef();
		tmp.setX(x);
		tmp.setY(y);
		points.getXYCoef().add(tmp);
	}

	@Override
	public int getPointsCount() {
		return points.getXYCoef().size();
	}

	public static void main(String[] args) {
		// Normal work
		System.out.println(new ArrayGFunction(-5,-5, 3,3).test("a", "f", -2, 2, 1));
		// Entering an odd amount of digits
		System.out.println(new ArrayGFunction(1,0,3).test("a", "f", -2, 2, 1));
	}
}
