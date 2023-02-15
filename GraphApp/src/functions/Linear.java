package functions;

public class Linear extends Function{
	private double Coefficient;
	private double Exponent;
	
	public Linear()
	{
		this.Coefficient = 0;
		this.Exponent = 0;
	}
	
	public Linear(double coefficient, double exponent)
	{
		this.Coefficient = coefficient;
		this.Exponent = exponent;
	}
	
	public double EvaluateAt(double x)
	{
		return Coefficient * Math.pow(x, Exponent);
	}
}
