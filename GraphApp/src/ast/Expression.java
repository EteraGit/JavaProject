package ast;

import java.util.ArrayList;
import java.util.List;

public class Expression {
	
	public List<Term> Terms;
	
	public Expression()
	{
		this.Terms = new ArrayList<Term>();
	}
	
	public Expression(List<Term> Terms)
	{
		this.Terms = Terms;
	}
	
	public double Compute(double x)
	{
		double sum = 0;
		for(Term term : Terms)
		{
			sum += term.Compute(x);
		}
		
		return sum;
	}
}
