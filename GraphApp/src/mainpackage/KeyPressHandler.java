package mainpackage;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import parser.Token;
import parser.TokenList;
import parser.TokenType;

public class KeyPressHandler extends KeyAdapter {
	public KeyPressHandler() {}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		String function = "f(x) = 3*x + sin(x)";
		
		//zelimo automatizirat ovaj proces ispod sa Lekser(function)
		//TokenList Tokens = parser.Lekser(function);
		
		TokenList Tokens = new TokenList();
		Tokens.addToken(new Token(TokenType.NAME, "f"));
		Tokens.addToken(new Token(TokenType.OPENED));
		Tokens.addToken(new Token(TokenType.X));
		Tokens.addToken(new Token(TokenType.CLOSED));
		Tokens.addToken(new Token(TokenType.EQUALS));
		Tokens.addToken(new Token(TokenType.NUMBER, "3"));
		Tokens.addToken(new Token(TokenType.TIMES));
		Tokens.addToken(new Token(TokenType.X));
		Tokens.addToken(new Token(TokenType.PLUS));
		Tokens.addToken(new Token(TokenType.SIN));
		Tokens.addToken(new Token(TokenType.OPENED));
		Tokens.addToken(new Token(TokenType.X));
		Tokens.addToken(new Token(TokenType.CLOSED));
		
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
