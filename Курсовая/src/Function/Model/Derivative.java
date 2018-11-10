package Function.Model;
public interface Derivative {
	double f(double x);
	
	static double findDerivative(double x, Derivative func) {
		return (func.f(x+0.0000001)-func.f(x))/0.0000001;
	}
}
