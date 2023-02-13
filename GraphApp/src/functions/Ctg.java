package functions;

public class Ctg extends Function{
	
	@Override
	public double EvaluateAt(double x)
	{
		return (double) 1.0 / Math.tan(x);
	}
}
