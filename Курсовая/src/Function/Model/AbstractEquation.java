package Function.Model;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public abstract class AbstractEquation {
	private ExtendedFunction f;
	private ExtendedFunction g;
	private List<Double> root;
	
	public AbstractEquation clearRoots() {
		root = new ArrayList<Double>();
		return this;
	}
	
	public ExtendedFunction getF() {
		return f;
	}
	
	public AbstractEquation setF(ExtendedFunction f) {
		this.f = f;
		return clearRoots();
	}
	
	public ExtendedFunction getG() {
		return g;
	}
	
	public AbstractEquation setG(ExtendedFunction g) {
		this.g = g;
		return clearRoots();
	}
	
	public AbstractEquation setABC(ExtendedFunction f, ExtendedFunction g) {
		this.f = f;
		this.g = g;
		return clearRoots();
	}
	
	public AbstractEquation solve(double from, double to, double eps, int n) {
		if((f!=null)&&(g!=null)) {
			double part = (to>=0?to-from:-from+to)/n; // finding the length of one part from n equal parts
			//System.out.println(whole);
			for (double a=from; a<to; a+=part) {
				//System.out.println("Finding roots at "+a+" "+(a+part));
				double xPrev = a, xCurr = a+part;
				double xNext=0, tmp = 0;
				if((f.y(xPrev)-g.y(xPrev))*(f.y(xCurr)-g.y(xCurr))<=0) {
					do{
						tmp = xNext;
						xNext = xCurr - (f.y(xCurr)-g.y(xCurr))*(xPrev - xCurr) / ((f.y(xPrev)-g.y(xPrev)) - (f.y(xCurr)-g.y(xCurr)));
						xPrev=xCurr;
						xCurr = tmp;
					}while (Math.abs(xNext-xCurr)>eps);
//					System.out.println(new DecimalFormat("#0.00").format(xNext));
					Double result = Double.valueOf(xNext);
					if (!root.contains(result)&&!Double.isNaN(xNext)) {
						root.add(Double.valueOf(new DecimalFormat("#0.00").format(xNext)));
					}
				}
			}
		}
		return this;
	}
	
	public List<Double> getRoots(){
		return root==null?null: root;
	}
	
	@Override
	public String toString() {
		if ((f==null)||(g==null)) return "Не все функции заданы либо не вызван метод solve";
		if (root.size()==0) return "Корень не найден";
		return root.toString();
	}

	public static void main(String[] args) throws Exception{
		class TestFunction extends AbstractEquation{ // Why should I create this class?
		}
		
		TestFunction tf = new TestFunction();
		// The function isn't solved
		System.out.println(tf);
		// The functions f and g weren't set
		System.out.println(tf.solve(-1, 1, 0.001, 2));
		// The functions f or g wasn't set
		System.out.println(tf.setF(x->1).solve(-1, 1, 0.001, 2));
		// Functions don't intercourse (f(x)=1, g(x)=2)
		System.out.println(tf.setABC(x->1, x->2).solve(-1, 1, 0.001, 2));
		// Normal work f(x)=x g(x)=-x
		System.out.println(tf.setABC(x->x, x->-x).solve(-1, 1, 0.001, 2));
		// Function has two roots f(x)=-x^2+1 g(x)=x^2-1
		System.out.println(tf.setABC(x->-x*x+1, x->x*x-1).solve(-5, 5, 0.001, 100));
	}
}
