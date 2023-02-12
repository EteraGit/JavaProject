package functions;

import ast.Function;

public class Sin extends Function{
	
	@Override
	public double EvaluateAt(double x)
	{
		return Math.sin(x);
	}
}
