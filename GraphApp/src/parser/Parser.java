package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import ast.Expression;
import ast.Factor;
import ast.Function;
import ast.Term;
import functions.Linear;
import functions.Sin;
import mainpackage.Pair;

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
			
			//not an operator
			
			if(Character.isAlphabetic(trenutni))
			{
				name += trenutni;
				
				if(Character.isAlphabetic(ulaz.charAt(i+1))) continue;
				else if(ulaz.charAt(i+1) != '(') 
				{
					Error.showError("No open parentheses after the function named  " + name + "!");
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
				
				if(i + 1 < ulaz.length())
				{
					if(!Character.isDigit(ulaz.charAt(i+1)))
					{
						Tokens.addToken(new Token(TokenType.NUMBER, broj));
						broj = "";
						continue;
					}
				}
				else
				{
					Tokens.addToken(new Token(TokenType.NUMBER, broj));
					broj = "";
					continue;
				}
			}	
		}
		return Tokens;
	}
	
	public Expression Parse(TokenList Tokens)
	{
		/*
		 According to the context-free grammar:
		 
		 Expression -> Term | Term PM Expression
		 Term -> Factor | Factor MD Term
		 Factor -> LIN | Function(Expression) | Expression
		 Function -> SIN | COS | TAN | LOG | ...
		 PM -> + | -
		 MD -> * | /
		 */
		
		return ParseExpression(Tokens);
	}


	private Expression ParseExpression(TokenList Tokens) 
	{
		if(Tokens.tokenAt(0).type == TokenType.OPENED && Tokens.tokenAt(Tokens.Length() - 1).type == TokenType.CLOSED)
		{
			Tokens.remove(0);
			Tokens.remove(Tokens.Length() - 1);
		}
		
		List<TokenList> ResultList = new ArrayList<TokenList>();
		
		int numOpened = 0; int numClosed = 0;
		
		TokenList CurrentList = new TokenList();
		
		for(int i = 0; i < Tokens.Length(); i++)
		{
			switch(Tokens.tokenAt(i).type)
			{
			case OPENED:
				numOpened++;
				CurrentList.addToken(Tokens.tokenAt(i));
				break;
			case CLOSED:
				numClosed++;
				CurrentList.addToken(Tokens.tokenAt(i));
				break;
			case PLUS:
				if(numOpened == numClosed)
				{
					ResultList.add(CurrentList);
					CurrentList = new TokenList();
				}
				else CurrentList.addToken(Tokens.tokenAt(i));
				break;
			case MINUS:
				if(numOpened == numClosed)
				{
					ResultList.add(CurrentList);
					CurrentList = new TokenList();
				}
				else CurrentList.addToken(Tokens.tokenAt(i));
				break;
			default:
				if(numOpened < numClosed) 
					Error.showError("Number of closed brackets is bigger than the number of opened brackets!");
				
				CurrentList.addToken(Tokens.tokenAt(i));
				break;
			}
		}
		if(numOpened > numClosed) 
			Error.showError("Number of opened brackets is bigger than the number of closed brackets!");
		
		if(numOpened == numClosed && CurrentList.Length() != 0) ResultList.add(CurrentList);
		
		List<Term> Terms = new ArrayList<Term>();
		for(int i = 0; i < ResultList.size(); i++)
		{
			Terms.add(ParseTerm(ResultList.get(i)));
		}
		
		return new Expression(Terms);
	}

	private Term ParseTerm(TokenList Term) 
	{
		if(Term.tokenAt(0).type == TokenType.OPENED && Term.tokenAt(Term.Length() - 1).type == TokenType.CLOSED)
		{
			Term.remove(0);
			Term.remove(Term.Length() - 1);
		}
		
		List<TokenList> ResultList = new ArrayList<TokenList>();
		
		int numOpened = 0; int numClosed = 0;
		
		TokenList CurrentList = new TokenList();
		
		for(int i = 0; i < Term.Length(); i++)
		{
			switch(Term.tokenAt(i).type)
			{
			case OPENED:
				numOpened++;
				CurrentList.addToken(Term.tokenAt(i));
				break;
			case CLOSED:
				numClosed++;
				CurrentList.addToken(Term.tokenAt(i));
				break;
			case TIMES:
				if(numOpened == numClosed)
				{
					ResultList.add(CurrentList);
					CurrentList = new TokenList();
				}
				else CurrentList.addToken(Term.tokenAt(i));
				break;
			case DIV:
				if(numOpened == numClosed)
				{
					ResultList.add(CurrentList);
					CurrentList = new TokenList();
				}
				else CurrentList.addToken(Term.tokenAt(i));
				break;
			default:
				if(numOpened < numClosed) 
					Error.showError("Number of closed brackets is bigger than the number of opened brackets!");
				CurrentList.addToken(Term.tokenAt(i));
				break;
			}
		}
		if(numOpened > numClosed) 
			Error.showError("Number of opened brackets is bigger than the number of closed brackets!");
		
		if(numOpened == numClosed && CurrentList.Length() != 0)
			ResultList.add(CurrentList);
		
		
		List<Factor> Factors = new ArrayList<Factor>();
		for(int i = 0; i < ResultList.size(); i++)
		{
			Factors.add(ParseFactor(ResultList.get(i)));
		}
		
		return new Term(Factors);
	}
	
	private Factor ParseFactor(TokenList Factor) 
	{
		if(Factor.tokenAt(0).type == TokenType.OPENED && Factor.tokenAt(Factor.Length() - 1).type == TokenType.CLOSED)
		{
			Factor.remove(0);
			Factor.remove(Factor.Length() - 1);
		}
		
		if(isLinear(Factor)) 
		{
			switch(Factor.Length())
			{
			case 1:
				if(Factor.tokenAt(0).type == TokenType.X) 
					return new Factor(new Linear(1.0,1.0));
				else if(Factor.tokenAt(0).type == TokenType.NUMBER) 
					return new Factor(new Linear(Double.parseDouble(Factor.tokenAt(0).value), 0.0));
				else 
					return new Factor(new Linear());
			case 2:
				return new Factor(new Linear(Double.parseDouble(Factor.tokenAt(0).value), 1.0));
			case 3:
				if(Factor.tokenAt(0).type == TokenType.NUMBER && 
				   Factor.tokenAt(1).type == TokenType.TIMES &&
				   Factor.tokenAt(2).type == TokenType.X)
					return new Factor(new Linear(Double.parseDouble(Factor.tokenAt(0).value), 
								  			 	 1.0));
				if(Factor.tokenAt(0).type == TokenType.X && 
				   Factor.tokenAt(1).type == TokenType.POW &&
				   Factor.tokenAt(2).type == TokenType.NUMBER)
					return new Factor(new Linear(1.0, 
												 Double.parseDouble(Factor.tokenAt(2).value)));
				if(Factor.tokenAt(0).type == TokenType.NUMBER && 
				   Factor.tokenAt(1).type == TokenType.POW &&
				   Factor.tokenAt(2).type == TokenType.NUMBER)
					return new Factor(new Linear(Double.parseDouble(Factor.tokenAt(0).value), 
							 					 Double.parseDouble(Factor.tokenAt(2).value)));
			case 4:
				return new Factor(new Linear(Double.parseDouble(Factor.tokenAt(0).value), 
	 					 				     Double.parseDouble(Factor.tokenAt(3).value)));
			case 5:
				return new Factor(new Linear(Double.parseDouble(Factor.tokenAt(0).value), 
	 				     					 Double.parseDouble(Factor.tokenAt(4).value)));
			default:
				return new Factor(new Linear());
			}
		}			
		else if(TokenType.FUNCTIONS.contains(Factor.tokenAt(0).type))
		{
			Function function;
			switch(Factor.tokenAt(0).type)
			{
			case SIN:
				function = new Sin();
				break;
			//add all functions
			default:
				function = new Function();
				break;
			}
			
			Factor.remove(0);
			
			return new Factor(function, ParseExpression(Factor));
		}
		
		return new Factor(new Function(), ParseExpression(Factor));
	}

	private boolean isLinear(TokenList Factor) 
	{
		switch(Factor.Length())
		{
		case 1:
			if(Factor.tokenAt(0).type == TokenType.X || Factor.tokenAt(0).type == TokenType.NUMBER)
				return true;
			break;
		case 2:
			if(Factor.tokenAt(0).type == TokenType.NUMBER && Factor.tokenAt(1).type == TokenType.X)
				return true;
			break;
		case 3:
			if(Factor.tokenAt(0).type == TokenType.NUMBER && 
			   Factor.tokenAt(1).type == TokenType.TIMES &&
			   Factor.tokenAt(2).type == TokenType.X)
				return true;
			if(Factor.tokenAt(0).type == TokenType.X && 
			   Factor.tokenAt(1).type == TokenType.POW &&
			   Factor.tokenAt(2).type == TokenType.NUMBER)
				return true;
			if(Factor.tokenAt(0).type == TokenType.NUMBER && 
			   Factor.tokenAt(1).type == TokenType.POW &&
			   Factor.tokenAt(2).type == TokenType.NUMBER)
				return true;
			break;
		case 4:
			if(Factor.tokenAt(0).type == TokenType.NUMBER && 
			   Factor.tokenAt(1).type == TokenType.X &&
			   Factor.tokenAt(2).type == TokenType.POW &&
			   Factor.tokenAt(3).type == TokenType.NUMBER)
				return true;
			break;
		case 5:
			if(Factor.tokenAt(0).type == TokenType.NUMBER && 
			   Factor.tokenAt(1).type == TokenType.TIMES &&
			   Factor.tokenAt(2).type == TokenType.X &&
			   Factor.tokenAt(3).type == TokenType.POW &&
			   Factor.tokenAt(4).type == TokenType.NUMBER)
				return true;
			break;
		default:
			return false;	
		}
		return false;
	}
}

