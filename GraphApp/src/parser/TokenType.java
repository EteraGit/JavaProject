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
	SIN("sin"),
	COS("cos"),
	TAN("tan"),
	CTG("ctg"),
	LOG("log"),
	ABS("abs"),
	CEILING("ceil"),
	FLOOR("floor"),
	SQUARE_ROOT("sqrt"),
	COMMA(","),
	X("x"),
	NUMBER(""),
	NAME("");
	
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
}
