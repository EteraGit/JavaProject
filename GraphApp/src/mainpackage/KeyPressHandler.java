package mainpackage;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import ast.Expression;
import parser.Parser;
import parser.TokenList;

public class KeyPressHandler extends KeyAdapter {
	
	public Parser parser;
	
	public KeyPressHandler() 
	{
		parser = new Parser();
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(event.getKeyCode() == KeyEvent.VK_ENTER)
		{						
			TokenList Tokens = parser.Lekser(Panels.functionsPanel.functionInput.getText());
			
			Expression expression = parser.Parse(Tokens);
			
			List<Tocka> newFunction = new ArrayList<Tocka>();
			for(int i = 0; i < Panels.functionsPanel.Preciznost; i++)
			{
				double broj_x = Panels.functionsPanel.x_left +
								((double) i / (double) Panels.functionsPanel.Preciznost) *
								Math.abs(Panels.functionsPanel.x_right - Panels.functionsPanel.x_left);
				double broj_y = expression.Compute(broj_x);
				
				newFunction.add(new Tocka(broj_x, broj_y));
			}
			
			Panels.functionsPanel.setFunction(newFunction);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent event)
	{
		
	}
	
	@Override
	public void keyTyped(KeyEvent event)
	{
		
	}
}
