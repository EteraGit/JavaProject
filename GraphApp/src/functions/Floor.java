package functions;

public class Floor extends Function{
	
	@Override
	public double EvaluateAt(double x)
	{
		return Math.floor(x);
	}
}
