package Function.Model;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public abstract class AbstractEquation {
	private ExtendedFunction f;
	private ExtendedFunction g;
	private List<Double> roots;
	
	public AbstractEquation clearRoots() {
		roots = new ArrayList<Double>();
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
		clearRoots();
		if((f!=null)&&(g!=null)) {
			double part = (to>=0?to-from:-from+to)/n; // finding the length of one part from n equal parts
			Derivative func = x->f.y(x)-g.y(x);
			for (double xCurr=from; xCurr<to; xCurr+=part) {
				//System.out.println("Finding roots at "+xCurr+" "+(xCurr+part));
				double xNext, tmp;
				// Checks if F(a)*F(b)<0
				if((f.y(xCurr)-g.y(xCurr))*(f.y(xCurr+part)-g.y(xCurr+part))<=0) {
//Chords Method

					double fstDer = Derivative.findDerivative(xCurr, func);
					double sndDer = Derivative.findDerivative(xCurr, x->Derivative.findDerivative(x, func));
					//System.out.println(fstDer+" "+sndDer);
					if (fstDer*sndDer<0) {
						xNext = xCurr+part;
						do{
							tmp = xNext;
							xNext = tmp - (f.y(tmp)-g.y(tmp))*(tmp - xCurr) / ((f.y(tmp)-g.y(tmp)) - (f.y(xCurr)-g.y(xCurr)));
							//System.out.println("xNext: "+xNext);
						}while ((Math.abs(xNext-tmp)>eps)&&(tmp>xNext));
					}
					else{
						xNext = xCurr;
						do{
							tmp = xNext;
							xNext = tmp - (f.y(tmp)-g.y(tmp))*(xCurr+part - tmp) / ((f.y(xCurr+part)-g.y(xCurr+part)) - (f.y(tmp)-g.y(tmp)));
							//System.out.println("xNext: "+xNext);
						}while ((Math.abs(xNext-tmp)>eps)&&(tmp<xNext));
					}

					//System.out.println(xNext);
					if (!roots.contains(Double.valueOf(xNext))&&!Double.isNaN(xNext)) {
						roots.add(Double.valueOf(xNext));
					}
				}
			}
		}
		return this;
	}
	
	public List<Double> getRoots(){
		return roots==null?null: roots;
	}
	
	@Override
	public String toString() {
		if ((f==null)||(g==null)) return "Не все функции заданы либо не вызван метод solve";
		if (roots.size()==0) return "Корень не найден";
		return roots.toString();
	}

	public static void main(String[] args) throws Exception{
		class TestFunction extends AbstractEquation{ // Why should I create this class?
		}
		
		TestFunction tf = new TestFunction();
//		 The function wasn't solved
		System.out.println(tf);
//		 The functions f and g weren't set
		System.out.println(tf.solve(-1, 1, 0.001, 100));
//		 The functions f or g wasn't set
		System.out.println(tf.setF(x->1).solve(-1, 1, 0.001, 2));
		
		ArrayGFunction g = new ArrayGFunction();
		g.addPoint(-1, 1);
		g.addPoint(0, 0);
		g.addPoint(1, 4);
		g.addPoint(2, 0);
		g.addPoint(3, 4);
		System.out.println(tf.setABC(x->3,g).solve(-5, 5, 0.0001, 100));
		// Function has two roots f(x)=-x^2+1 g(x)=x^2-1
		System.out.println(tf.setABC(x->-x*x+1,x->x*x-1).solve(-2, 2, 0.001, 2));

		// Functions don't intercourse (f(x)=1, g(x)=2)
		System.out.println(tf.setABC(x->1, x->2).solve(-1, 1, 0.001, 2));
		// Normal work f(x)=x g(x)=-x
		System.out.println(tf.setABC(x->x, x->-x).solve(-1, 1, 0.001, 2));
	}
}
