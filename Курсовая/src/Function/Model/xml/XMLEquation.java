package Function.Model.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.*;
import java.io.*;


import Function.Model.AbstractEquation;
import Function.Model.AbstractFFunction;
import Function.Model.AbstractGFunction;

public class XMLEquation extends AbstractEquation {

	@SuppressWarnings("serial")
    public static class FileException extends Exception {
        private String fileName;

        public FileException(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    @SuppressWarnings("serial")
    public static class FileReadException extends FileException {
        public FileReadException(String fileName) {
            super(fileName);
        }
    }
    @SuppressWarnings("serial")
    public static class FileWriteException extends FileException {
        public FileWriteException(String fileName) {
            super(fileName);
        }
    }
       
    private EquationData data;

    public XMLEquation() {
        clearEquation();
    }

    public XMLEquation(String fileName) {
        try {
            readFromFile(fileName);
        }
        catch (FileReadException ex) {
            throw new RuntimeException("Error reading " + fileName);
        }
    }

    public XMLEquation clearEquation() {
//		data = new EquationData();
//        setF(new XMLFFunction(data));
//        setG(new XMLGFunction(data));
    	try {
			return readFromFile("src/Function/Model/xml/samples/blank.xml");
		} catch (FileReadException e) {
			e.printStackTrace();
		}
		return this;
    }

    public EquationData getData() {
        return data;
    }

    public AbstractFFunction getFFunction() {
        return (AbstractFFunction) getF();
    }

    public AbstractGFunction getGFunction() {
        return (AbstractGFunction) getG();
    }
    
    public XMLEquation readFromFile(String fileName) throws FileReadException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("Function.Model.xml");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            data = (EquationData) unmarshaller.unmarshal(new FileInputStream(fileName));
            setF(new XMLFFunction(data));
            setG(new XMLGFunction(data));
            return this;
        }
        catch (FileNotFoundException | JAXBException e) {
            throw new FileReadException(fileName);
        }
    }
    
    public XMLEquation writeToFile(String fileName) throws FileWriteException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("Function.Model.xml");
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(data, new FileWriter(fileName));
            return this;
        }
        catch (IOException | JAXBException e) {
            throw new FileWriteException(fileName);
        }
    }
    
    public XMLEquation saveReport(String fileName, String imageName) throws FileWriteException {
        try (PrintWriter out = new PrintWriter(
          new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
            out.printf("<html>%n");

            out.printf("<head>%n");
            out.printf("<meta http-equiv='Content-Type' content='text/html; " + 
                       "charset=UTF-8'>%n");
            out.printf("</head>%n");
            out.printf("<body>%n");
            out.printf("<h2>Отчет</h2>%n");
            out.printf("<p>В результате решения уравнения f(x)-g(x) " +
                       "с такими исходными данными:</p>%n");
            out.printf("<h4>Дані для функції <span style='font-family:Times, Serif;'>" + 
                       "<em>f(t)</em></span></h4>%n");
            out.printf("<table border = '1' cellpadding=4 cellspacing=0>%n");
            out.printf("<tr>%n");
            out.printf("<th>Індекс</th>%n");
            out.printf("<th>a</th>%n");
            out.printf("</tr>%n");
            out.printf("<td>%n");
            for (int i = 0; i < getFFunction().getCoefCount(); i++) {
                out.printf("<tr>%n");
                out.printf("<td>%d</td>", i);
                out.printf("<td>%8.3f</td>%n", getFFunction().getCoef(i));
                out.printf("</tr>%n");
            }
            out.printf("</table>%n");
            out.printf("<h4>Дані для функції <span style='font-family:Times, Serif;'>" +
                       "<em>g(t)</em></span></h4>%n");
            out.printf("<table border = '1' cellpadding=4 cellspacing=0>%n");
            out.printf("<tr>%n");
            out.printf("<th>Індекс</th>%n");
            out.printf("<th>x</th>%n");
            out.printf("<th>y</th>%n");
            out.printf("</tr>%n");
            out.printf("<td>%n");
            for (int i = 0; i < getGFunction().getPointsCount(); i++) {
                out.printf("<tr>%n");
                out.printf("<td>%d</td>", i);
                out.printf("<td>%8.3f</td>%n", getGFunction().getX(i));
                out.printf("<td>%8.3f</td>%n", getGFunction().getY(i));
                out.printf("</tr>%n");
            }
            out.printf("</table>%n");
//            out.printf("<p>Значення параметру <em>t</em>: %8.3f</p>%n", t);
//            solve(t);
//            switch (getRoots()) {
//                case 0:
//                    out.printf("<p>було встановлено, що рівняння не має коренів.%n</p>");
//                    break;
//                case 1:
//                    if (getRoots().get(0) == null) {
//                        out.printf("<p>було встановлено, що рівняння має безліч коренів.</p>%n");
//                    }
//                    else {
//                        out.printf("<p>був отриманий такий корінь: %s</p>%n", getRoots().get(0));
//                    }
//                    break;
//                case 2:
//                    out.printf("<p>були отримані такі корені:</p>%n");
//                    for (Double root : getRoots()) {
//                        out.printf("%8.3f<br>%n", root);
//                    }
//                    break;
//                default: 
//                    throw new RuntimeException("Unknown error");
//            }
            if (imageName != null) {
                out.printf("<img src = \"" + imageName + "\"/>");
            }
            out.printf("</body>%n");
            out.printf("</html>%n");
            return this;
        }
        catch (IOException e) {
            throw new FileWriteException(fileName);
        }
    }

//}

}
