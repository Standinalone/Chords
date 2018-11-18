package Function.Model;

import java.util.function.DoubleUnaryOperator;

public interface ExtendedFunction extends DoubleUnaryOperator{
	default double y (double x) {
		return applyAsDouble(x);
	}
	
	default String test (String argName, String funcName, double from, double to, double step) {
		StringBuilder sb = new StringBuilder();
		for (double x = from; x<=to; x+=step) {
			sb.append(String.format(argName + " = %.4f " + funcName + "(" + argName + ") = %.4f ",x,y(x)));
		}
		return sb.toString();
	}
}
