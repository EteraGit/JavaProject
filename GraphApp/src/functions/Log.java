package functions;

public class Log extends Function{
	
	@Override
	public double EvaluateAt(double x)
	{
		if(x>=0 && x<=0.01) return Double.NaN;
		return Math.log(x);
	}
}
