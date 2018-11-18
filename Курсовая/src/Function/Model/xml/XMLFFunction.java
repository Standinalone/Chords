package Function.Model.xml;

import Function.Model.AbstractFFunction;
import Function.Model.ArrayFFunction;
import Function.Model.xml.EquationData.Coefs.Coef;

public class XMLFFunction extends AbstractFFunction{

	private EquationData.Coefs coefs;
	public XMLFFunction(EquationData data) {
		this.coefs = data.coefs;
	}
	@Override
	public double getCoef(int i) {
		return coefs.getCoef().get(i).value; // getCoef return the list of coefficients
	}

	@Override
	public void setCoef(int i, double coef) {
		coefs.getCoef().get(i).setValue(coef);;
	}

	@Override
	public int getCoefCount() {
		return coefs.getCoef().size();
	}
	
	@Override
	public void addCoef(double coef) {
		Coef tmp = new Coef();
		tmp.setIndex(coefs.getCoef().size());
		tmp.setValue(coef);
		coefs.getCoef().add(tmp);
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
