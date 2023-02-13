package parser;

import java.util.ArrayList;
import java.util.List;

public class TokenList 
{
	private List<Token> tokens;
	
	public TokenList() {
		tokens = new ArrayList<>();
	}
	
	@SuppressWarnings("unused")
	private TokenList(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	public void addToken(Token token) {tokens.add(token);}
	
	public Token tokenAt(int i) {return tokens.get(i);}
	
	public int Length() {return tokens.size();}
	
	public void insert(int i, Token token) {tokens.add(i, token);}
	
	public void addAll(int i, List<Token> newTokens) { tokens.addAll(i, newTokens); }
	
	public Token remove(int i) {return tokens.remove(i);}
	
	public List<Token> getList() { return tokens; }
	
	public void clear() {tokens = new ArrayList<Token>();}
	
	public int numTokensOfType(TokenType type) { 
		int count = 0;
		for(Token token : tokens)
		{
			if(type == token.type) count++;
		}
		return count;
	}
	
	@Override
	public String toString() 
	{
		String line = "";
		for (Token t : tokens) 
		{
			line += t.toString();
			if (t.hasValue()) line += "(" + t.value + ")";
			line += " ";
		}
		return line;
	}
}
