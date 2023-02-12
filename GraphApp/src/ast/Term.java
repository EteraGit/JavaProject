package ast;

import java.util.ArrayList;
import java.util.List;

public class Term {
	
	public List<Factor> Factors;
	
	public Term()
	{
		this.Factors = new ArrayList<Factor>();
	}
	
	public Term(List<Factor> Factors)
	{
		this.Factors = Factors;
	}
	
	public double Compute(double x)
	{
		double mul = 1;
		for(Factor factor : Factors)
		{
			mul *= factor.Compute(x);
		}
		
		return mul;
	}
}
