package parser;

import java.util.Vector;

public class Parser {
	
	Vector<Token> CustomFunctions;
	
	public Parser() 
	{
		CustomFunctions = new Vector<Token>();
	}
	
	public TokenList Lekser(String ulaz)
	{
		TokenList Tokens = new TokenList();
		
		String broj = "";
		String name = "";
		
		for(int i = 0; i < ulaz.length(); i++)
		{
			char trenutni = ulaz.charAt(i);
			
			if(trenutni == ' ') continue;
			
			if(Character.toString(trenutni).equals(TokenType.OPENED.symbol))
			{
				Tokens.addToken(new Token(TokenType.OPENED));
				continue;
			}
			else if(Character.toString(trenutni).equals(TokenType.CLOSED.symbol))
			{
				Tokens.addToken(new Token(TokenType.CLOSED));
				continue;
			}
			else if(Character.toString(trenutni).equals(TokenType.EQUALS.symbol))
			{
				Tokens.addToken(new Token(TokenType.EQUALS));
				continue;
			}
			else if(Character.toString(trenutni).equals(TokenType.PLUS.symbol))
			{
				Tokens.addToken(new Token(TokenType.PLUS));
				continue;
			}
			else if(Character.toString(trenutni).equals(TokenType.MINUS.symbol))
			{
				Tokens.addToken(new Token(TokenType.MINUS));
				continue;
			}
			else if(Character.toString(trenutni).equals(TokenType.TIMES.symbol))
			{
				Tokens.addToken(new Token(TokenType.TIMES));
				continue;
			}
			else if(Character.toString(trenutni).equals(TokenType.DIV.symbol))
			{
				Tokens.addToken(new Token(TokenType.DIV));
				continue;
			}
			else if(Character.toString(trenutni).equals(TokenType.DOT.symbol))
			{
				Tokens.addToken(new Token(TokenType.DOT));
				continue;
			}
			else if(Character.toString(trenutni).equals(TokenType.POW.symbol))
			{
				Tokens.addToken(new Token(TokenType.POW));
				continue;
			}
			else if(Character.toString(trenutni).equals(TokenType.X.symbol))
			{
				Tokens.addToken(new Token(TokenType.X));
				continue;
			}
			
			//nije operator
			
			if(Character.isAlphabetic(trenutni))
			{
				name += trenutni;
				
				if(Character.isAlphabetic(ulaz.charAt(i+1))) continue;
				else if(ulaz.charAt(i+1) != '(') 
				{
					System.out.println("Nema otvorene zagrade nakon imena " + name + " funkcije");
					System.exit(0);
				}
				else
				{
					if(!TokenType.getTokenType(name).symbol.equals("null"))
					{
						Tokens.addToken(new Token(TokenType.getTokenType(name)));
						name = "";
						continue;
					}
					else
					{
						Tokens.addToken(new Token(TokenType.NAME, name));
						CustomFunctions.add(new Token(TokenType.NAME, name));
						name = "";
						continue;
					}
				}
			}
			
			if(Character.isDigit(trenutni))
			{
				broj += trenutni;
				
				if(!Character.isDigit(ulaz.charAt(i+1)))
				{
					Tokens.addToken(new Token(TokenType.NUMBER, broj));
					broj = "";
					continue;
				}
			}	
		}
		return Tokens;
	}
}

