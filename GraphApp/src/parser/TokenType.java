package parser;

import java.util.ArrayList;
import java.util.List;

public enum TokenType 
{
	OPENED("("),
	CLOSED(")"),
	EQUALS("="),
	PLUS("+"),
	MINUS("-"),
	TIMES("*"),
	DIV("/"),
	POW("^"),
	DOT("."),
	SIN("sin"),
	COS("cos"),
	TAN("tan"),
	CTG("ctg"),
	LOG("log"),
	ABS("abs"),
	CEILING("ceil"),
	FLOOR("floor"),
	SQRT("sqrt"),
	X("x"),
	NUMBER(""),
	NAME(""),
	EMPTY("null");
	
	public static final List<TokenType> FUNCTIONS = new ArrayList<TokenType>
	(List.of(SIN, COS, TAN, CTG, SQRT, CEILING, FLOOR, LOG, ABS));
	
	public final String symbol;
	
	private TokenType(String symbol) 
	{
		this.symbol = symbol;
	}
	
	public static TokenType getTokenType(String name)
	{
		for(TokenType t : TokenType.values())
		{
			if(t.symbol.equals(name)) return t;
		}
		return EMPTY;
	}
}
