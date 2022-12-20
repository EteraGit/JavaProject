package mainpackage;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import parser.Parser;
import parser.Token;
import parser.TokenList;
import parser.TokenType;

public class KeyPressHandler extends KeyAdapter {
	
	public Parser parser;
	
	public KeyPressHandler() 
	{
		parser = new Parser();
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		String function = "funkcija(x) = 3*x + 3*sin(cos(x)) + gt(tan(x))";
		
		//zelimo automatizirat ovaj proces ispod sa Lekser(function)
		TokenList Tokens = parser.Lekser(function);
		
		System.out.println(Tokens);
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
