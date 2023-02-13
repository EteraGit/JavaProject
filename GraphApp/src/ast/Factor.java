package ast;

import java.util.ArrayList;
import java.util.List;

public class Factor {
	
	public List<Power> Powers;
	
	public Factor()
	{
		this.Powers = new ArrayList<Power>();
	}
	
	public Factor(List<Power> Powers)
	{
		this.Powers = Powers;
	}
	
	public double Compute(double x)
	{		
		double pow = 1;
		for(int i = Powers.size() - 1; i >= 0; i--)
		{
			pow = Math.pow(Powers.get(i).Compute(x), pow);
		}
		return pow;		
	}
}
