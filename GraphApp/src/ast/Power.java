package ast;

import functions.Function;
import functions.Linear;

public class Power {

	public boolean isLinear;
	public Linear Lin;
	public Function Func;
	public Expression Expr;
	
	public Power()
	{
		this.isLinear = true;
		this.Lin = new Linear();
	}
	
	public Power(Linear lin)
	{
		this.isLinear = true;
		this.Lin = lin;
	}
	
	public Power(Function function, Expression expression)
	{
		this.isLinear = false;
		this.Func = function;
		this.Expr = expression;
	}
	
	public double Compute(double x)
	{
		if(isLinear)
		{
			return Lin.EvaluateAt(x);
		}
		else
		{
			return Func.EvaluateAt(Expr.Compute(x));
		}
	}
}
