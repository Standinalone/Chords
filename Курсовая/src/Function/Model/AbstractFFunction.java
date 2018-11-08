package Function.Model;

import java.text.DecimalFormat;

public abstract class AbstractFFunction implements ExtendedFunction {
	public abstract double getCoef(int i);
	public abstract void setCoef(int i, double coef);
	public abstract int getCoefCount();
	public abstract void addCoef(double coef);
	
	public String getFormula() {
		String formula = "";
		if (getCoefCount()>1) formula=new DecimalFormat("0.00").format(getCoef(0))+"x^"+(getCoefCount()-1);
		DecimalFormat fmt = new DecimalFormat(" + #,##0.0; - #");
		for (int i=1; i<getCoefCount()-1; i++) {
			formula += fmt.format(getCoef(i)) + "x^" + (getCoefCount()-i-1);
		}
		if ((getCoef(getCoefCount()-1))!=0) formula += fmt.format(getCoef(getCoefCount()-1));
		return formula;
	}
	
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
