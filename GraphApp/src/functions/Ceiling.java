package functions;

public class Ceiling extends Function{
	
	@Override
	public double EvaluateAt(double x)
	{
		return Math.ceil(x);
	}
}
