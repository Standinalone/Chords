package Function.Model.xml;

import Function.Model.xml.XMLEquation.FileException;

public class ConsoleApp {

	public static void main(String[] args) throws FileException {
		XMLEquation equation = new XMLEquation("src/Function/Model/xml/samples/CommonEquation.xml");
		// The equation wasn't solved
        System.out.println(equation);
        // Common case f(x)=x^2 g(x)=x
        System.out.println(equation.solve(-5,5,0.000001));
        
        equation.saveReport("Common.html", null);
        
        // Has no roots
        System.out.println(equation.readFromFile("src/Function/Model/xml/samples/EquationNoSolutions.xml").solve(-5,5,0.0001));

        //equation.saveReport("NoSolutions.html", null, 0);        
        
        // Creating an equation from scratch
        equation.clearEquation();
        equation.getFFunction().addCoef(2); // f(x) = 2
        equation.getFFunction().addCoef(0); // f(x) = 2x + 0
        equation.getGFunction().addPoint(1, 1);
        equation.getGFunction().addPoint(2, 1);
        
        System.out.println(equation.solve(-5,5,0.0001));
    }
}