package Function.Model.xml;

import Function.Model.xml.XMLEquation.FileException;

public class ConsoleApp {

	public static void main(String[] args) throws FileException {
		XMLEquation equation = new XMLEquation("src/Function/Model/xml/samples/CommonEquation.xml");
		// 1. The equation wasn't solved
        System.out.println(equation);
        // 2. Common case f(x)=x^2 g(x)=x
        System.out.println(equation.solve(-5,5,0.000001,10));
        equation.saveReport("Отчеты/Common.html", null);
        // 3. Has no roots
        System.out.println(equation.readFromFile("src/Function/Model/xml/samples/EquationNoSolutions.xml").solve(-5,5,0.0001,10));
        equation.saveReport("Отчеты/NoRoots.html", null);
        // 4. Has two roots
        equation.readFromFile("src/Function/Model/xml/samples/TwoRoots.xml");   
        System.out.println(equation.solve(-5, 5, 0.0001, 10));
        equation.saveReport("Отчеты/TwoRoots.html", null);
        // 5. Creating an equation from scratch
        equation.clearEquation();
        equation.getFFunction().addCoef(2); // f(x) = 2
        equation.getFFunction().addCoef(0); // f(x) = 2x + 0
        equation.getGFunction().addPoint(1, 1);
        equation.getGFunction().addPoint(2, 1);  // g(x) = 1 
        System.out.println(equation.solve(-5,5,0.0001,10));
        equation.saveReport("Отчеты/OneRoot.html", null);
    }
}