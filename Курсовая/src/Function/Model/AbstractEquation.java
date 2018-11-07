package Function.Model;

public abstract class AbstractEquation {
	private ExtendedFunction f;
	private ExtendedFunction g;
	private Double root;
	
	public AbstractEquation clearRoots() {
		root = null;
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
	
	public AbstractEquation solve(double xPrev, double xCurr, double eps) {
		if((f!=null)&&(g!=null)) {
			double xNext=0, tmp = 0;
			do{
				tmp = xNext;
				xNext = xCurr - (f.y(xCurr)-g.y(xCurr))*(xPrev - xCurr) / ((f.y(xPrev)-g.y(xPrev)) - (f.y(xCurr)-g.y(xCurr)));
				xPrev=xCurr;
				xCurr = tmp;
			}while (Math.abs(xNext-xCurr)>eps) ;
			if (!Double.isNaN(xNext)) root = xNext;
		}
		return this;
	}
	
	public double getRoots(){
		return root==null?null: root;
	}
	
	@Override
	public String toString() {
		if ((f==null)||(g==null)) return "Не все функции заданы либо не вызван метод solve";
		if (root==null) return "Корень не найден";
		return Double.toString(root);
	}

	public static void main(String[] args) throws Exception{
		class TestFunction extends AbstractEquation{ // Why should I to create this class?
		}
		
		TestFunction tf = new TestFunction();
		// The function isn't solved
		System.out.println(tf);
		// The functions f and g weren't set
		System.out.println(tf.solve(-1, 1, 0.001));
		// The functions f or g wasn't set
		System.out.println(tf.setF(x->1).solve(-1, 1, 0.001));
		// Functions don't intercourse (f(x)=1, g(x)=2)
		System.out.println(tf.setABC(x->1, x->2).solve(-1, 1, 0.001));
		// Normal work
		System.out.println(tf.setABC(x->x, x->-x).solve(-1, 1, 0.001));
	}
	
}
