package parser;

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
	SQUARE_ROOT("sqrt"),
	X("x"),
	NUMBER(""),
	NAME(""),
	PRAZAN("null");
	
	public static final TokenType[] FUNCTIONS = 
	{
		SIN, COS, TAN, CTG, SQUARE_ROOT,
		CEILING, FLOOR, LOG, ABS
	};
	
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
		return PRAZAN;
	}
}
