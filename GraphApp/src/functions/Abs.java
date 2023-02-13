package functions;

public class Abs extends Function{
	
	@Override
	public double EvaluateAt(double x)
	{
		return Math.abs(x);
	}
}