package ast;

import functions.Linear;

public class Factor {
	
	public boolean isLinear;
	public Linear Lin;
	public Function Func;
	public Expression Expr;
	
	public Factor()
	{
		this.isLinear = true;
		this.Lin = new Linear();
	}
	
	public Factor(Linear lin)
	{
		this.isLinear = true;
		this.Lin = lin;
	}
	
	public Factor(Function function, Expression expression)
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
