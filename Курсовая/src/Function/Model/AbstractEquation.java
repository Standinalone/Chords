package Function.Model;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEquation {
	private ExtendedFunction f;
	private ExtendedFunction g;
	private List<Double> roots;
	// The function h(x) we need to solve
	private ExtendedFunction h = x->f.y(x)-g.y(x);
	double pace = -0.000001;
	
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
		if((f!=null)&&(g!=null)) {
			final double part = (to-from)/n;
			//Checking for equal functions
			double xCurr=from;
			for (; xCurr<=to; xCurr+=part) {
				if (Math.round(h.y(xCurr)*100000)/100000!=0) break;
			}
			if (xCurr>=to) {
				roots.add(Double.POSITIVE_INFINITY);;
				return this;
			}
			//Copy of the main function h(x)
			ExtendedFunction h_copy = h;
			// Finding the length of one part from n equal parts
			final double pace_copy=pace;
			for (; pace<=0.000001; pace+=0.000001) {
				h=x->h_copy.y(x)+pace;
				// Defining a functional interfaces to find 1st and 2nd derivatives
				ExtendedFunction der1 = x->(h.y(x+0.0000001)-h.y(x))/0.0000001;
				ExtendedFunction der2 = x->(der1.y(x+0.0000001)-der1.y(x))/0.0000001;
				for (xCurr=from; xCurr<to; xCurr+=part) {
					double xNext, tmp;
					// Checks if h(a)*h(b)<0
					if(h.y(xCurr)*h.y(xCurr+part)<=0) {
						//Chord Method
						double fstDer = der1.y(xCurr);
						double sndDer = der2.y(xCurr);
						if (fstDer*sndDer<0) {
							xNext = xCurr+part;
							do{
								tmp = xNext;
								xNext = tmp - (h.y(tmp))*(tmp - xCurr) / (h.y(tmp) - h.y(xCurr));
							}while ((Math.abs(xNext-tmp)>eps)&&(tmp>xNext));
						}
						else{
							xNext = xCurr;
							do{
								tmp = xNext;
								xNext = tmp - (h.y(tmp))*(xCurr+part - tmp) / (h.y(xCurr+part) - h.y(tmp));
							}while ((Math.abs(xNext-tmp)>eps)&&(tmp<xNext));
						}
						// Eliminating unnecessary roots and adding new to the list of roots
						//System.out.println(xNext);
						if (!roots.contains(Double.valueOf(xNext))&&!Double.isNaN(xNext)) {
							boolean lock=true;
							for (double root : roots) {
								if (Math.abs(xNext-root)<0.001) {
									lock=false;
									break;
								}
							}
							//System.out.println(xNext);
							if(lock) roots.add(Double.valueOf(xNext));
						}
					}
				}
			}
			pace=pace_copy;
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
		// Creating new instance of a TestFunction class for testing
		class TestFunction extends AbstractEquation{}
		
		TestFunction tf = new TestFunction();
		System.out.println(tf.setABC(x->x*x,x->0).solve(-5, 5, 0.0001, 100));
		// The function wasn't solved
		System.out.println(tf);
		// The functions f and g weren't set
		System.out.println(tf.solve(-1, 1, 0.001, 100));
		// The functions f or g wasn't set
		System.out.println(tf.setF(x->1).solve(-1, 1, 0.001, 2));
		// Creating new g function
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
		// Two functions merge
		System.out.println(tf.setABC(x->1,x->1).solve(-5, 5, 0.0001, 100));
	}
}