package parser;

public class Token 
{
	public final TokenType type;
	public final String value;
	
	public Token(TokenType type) 
	{
		this.type = type;
		this.value = "";
	}
	
	public Token(TokenType type, String value) 
	{
		this.type = type;
		this.value = value;
	}
	
	public boolean hasValue()
	{
		if(value.length() > 0) return true;
		return false;
	}
	
	@Override
	public String toString() {	return type.toString(); }
}