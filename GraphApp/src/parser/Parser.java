package parser;

import java.util.ArrayList;
import java.util.List;

import ast.Power;
import ast.Expression;
import ast.Factor;
import ast.Term;
import functions.Abs;
import functions.Ceiling;
import functions.Cos;
import functions.Ctg;
import functions.CustomFunctions;
import functions.Floor;
import functions.Function;
import functions.Linear;
import functions.Log;
import functions.Sin;
import functions.Sqrt;
import functions.Tan;
import mainpackage.Error;
import mainpackage.Panels;
import mainpackage.Triple;

public class Parser {
	
	
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
		
		for(int i = 0; i < Tokens.Length(); i++)
		{
			if(i == 0 && Tokens.tokenAt(0).type == TokenType.MINUS)
			{
				Tokens.remove(0);
				Tokens.insert(0, new Token(TokenType.NUMBER, "-1"));
				Tokens.insert(1, new Token(TokenType.TIMES));
				i++;
			}
			else if(Tokens.tokenAt(i).type == TokenType.MINUS &&
					Tokens.tokenAt(i-1).type != TokenType.NUMBER && 
					Tokens.tokenAt(i-1).type != TokenType.X)
			{
				Tokens.remove(i);
				Tokens.insert(i, new Token(TokenType.PLUS));
				Tokens.insert(i+1, new Token(TokenType.NUMBER, "-1"));
				Tokens.insert(i+2, new Token(TokenType.TIMES));
				i += 2;
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
		 Factor -> Power | Power P Factor
		 Power -> LIN | Function(Expression) | Expression
		 Function -> SIN | COS | TAN | LOG | ...
		 PM -> + | -
		 MD -> * | /
		 P -> ^
		 */
		
		if(Tokens.Length() == 0) return new Expression();
		
		if(Tokens.tokenAt(0).type != TokenType.NAME || 
		   Tokens.tokenAt(1).type != TokenType.OPENED ||
		   Tokens.tokenAt(2).type != TokenType.X ||
		   Tokens.tokenAt(3).type != TokenType.CLOSED ||
		   Tokens.tokenAt(4).type != TokenType.EQUALS)
			Error.showError("Input should start with something like 'name(x) = ...'!");
		
		String expressionName = Tokens.tokenAt(0).value;
		
		for(int i = 0; i < 5; i++) Tokens.remove(0);
		
		Expression newExpression = ParseExpression(Tokens);
		
		for(int i = 0; i < CustomFunctions.expressions.size(); i++)
		{
			if(CustomFunctions.expressions.get(i).getL().equals(expressionName))
			{
				CustomFunctions.expressions.remove(i);
				break;
			}
		}
		CustomFunctions.expressions.add(new Triple<String, TokenList, Expression>(expressionName, Tokens, newExpression));
		
		boolean found = false;
        String[] lines = Panels.functionsPanel.functionNames.getText().split("\n");
        for (int i = 0; i < lines.length; i++) {
            if(lines[i].split("\\(")[0].equals(expressionName))
            {
            	lines[i] = Panels.functionsPanel.functionInput.getText() + "\n";
            	found = true;
            }
        }
		if(!found) Panels.functionsPanel.functionNames.append(Panels.functionsPanel.functionInput.getText() + "\n");
		else Panels.functionsPanel.functionNames.setText(String.join("\n", lines));
		
		return newExpression;
	}

	private Expression ParseExpression(TokenList Tokens) 
	{	
		if(Tokens.tokenAt(0).type == TokenType.OPENED && 
		   Tokens.tokenAt(Tokens.Length() - 1).type == TokenType.CLOSED &&
		   Tokens.numTokensOfType(TokenType.OPENED) == 1 &&
		   Tokens.numTokensOfType(TokenType.CLOSED) == 1)
		{
			Tokens.remove(0);
			Tokens.remove(Tokens.Length() - 1);
		}
		
		List<TokenList> ResultList = new ArrayList<TokenList>();
		
		int numOpened = 0; int numClosed = 0; boolean wasMinus = false;
		
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
					if(wasMinus)
					{
						CurrentList.insert(0, new Token(TokenType.NUMBER, "-1"));
						CurrentList.insert(1, new Token(TokenType.TIMES));
						CurrentList.insert(2, new Token(TokenType.OPENED));
						CurrentList.addToken(new Token(TokenType.CLOSED));
					}
					wasMinus = false;
					
					ResultList.add(CurrentList);
					CurrentList = new TokenList();
				}
				else CurrentList.addToken(Tokens.tokenAt(i));
				break;
			case MINUS:
				if(numOpened == numClosed)
				{
					if(wasMinus)
					{
						CurrentList.insert(0, new Token(TokenType.NUMBER, "-1"));
						CurrentList.insert(1, new Token(TokenType.TIMES));
						CurrentList.insert(2, new Token(TokenType.OPENED));
						CurrentList.addToken(new Token(TokenType.CLOSED));
					}
					wasMinus = true;
					
					ResultList.add(CurrentList);
					CurrentList = new TokenList();
				}
				else CurrentList.addToken(Tokens.tokenAt(i));
				break;
			default:				
				CurrentList.addToken(Tokens.tokenAt(i));
				break;
			}
		}
		if(numOpened < numClosed) 
			Error.showError("Number of closed brackets is bigger than the number of opened brackets!");
		if(numOpened > numClosed) 
			Error.showError("Number of opened brackets is bigger than the number of closed brackets!");
		
		if(wasMinus)
		{
			CurrentList.insert(0, new Token(TokenType.NUMBER, "-1"));
			CurrentList.insert(1, new Token(TokenType.TIMES));
			CurrentList.insert(2, new Token(TokenType.OPENED));
			CurrentList.addToken(new Token(TokenType.CLOSED));
		}
		
		if(numOpened == numClosed && CurrentList.Length() != 0) 
			ResultList.add(CurrentList);
		
		List<Term> Terms = new ArrayList<Term>();
		for(int i = 0; i < ResultList.size(); i++)
		{
			Terms.add(ParseTerm(ResultList.get(i)));
		}
		
		return new Expression(Terms);
	}

	private Term ParseTerm(TokenList Term) 
	{
		if(Term.tokenAt(0).type == TokenType.OPENED && 
		   Term.tokenAt(Term.Length() - 1).type == TokenType.CLOSED &&
		   Term.numTokensOfType(TokenType.OPENED) == 1 &&
		   Term.numTokensOfType(TokenType.CLOSED) == 1)
		{
			Term.remove(0);
			Term.remove(Term.Length() - 1);
		}
		
		List<TokenList> ResultList = new ArrayList<TokenList>();
		
		int numOpened = 0; int numClosed = 0; boolean wasDiv = false;
		
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
					if(wasDiv)
					{
						CurrentList.insert(0, new Token(TokenType.OPENED));
						CurrentList.addToken(new Token(TokenType.CLOSED));
						CurrentList.addToken(new Token(TokenType.POW));
						CurrentList.addToken(new Token(TokenType.NUMBER, "-1"));
					}
					wasDiv = false;
					
					ResultList.add(CurrentList);
					CurrentList = new TokenList();
				}
				else CurrentList.addToken(Term.tokenAt(i));
				break;
			case DIV:
				if(numOpened == numClosed)
				{
					if(wasDiv)
					{
						CurrentList.insert(0, new Token(TokenType.OPENED));
						CurrentList.addToken(new Token(TokenType.CLOSED));
						CurrentList.addToken(new Token(TokenType.POW));
						CurrentList.addToken(new Token(TokenType.NUMBER, "-1"));
					}
					wasDiv = true;
					
					ResultList.add(CurrentList);
					CurrentList = new TokenList();
				}
				else CurrentList.addToken(Term.tokenAt(i));
				break;
			default:
				CurrentList.addToken(Term.tokenAt(i));
				break;
			}
		}
		if(numOpened < numClosed) 
			Error.showError("Number of closed brackets is bigger than the number of opened brackets!");
		if(numOpened > numClosed) 
			Error.showError("Number of opened brackets is bigger than the number of closed brackets!");
		
		if(wasDiv)
		{
			CurrentList.insert(0, new Token(TokenType.OPENED));
			CurrentList.addToken(new Token(TokenType.CLOSED));
			CurrentList.addToken(new Token(TokenType.POW));
			CurrentList.addToken(new Token(TokenType.NUMBER, "-1"));
		}
		
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
		if(Factor.tokenAt(0).type == TokenType.OPENED && 
		   Factor.tokenAt(Factor.Length() - 1).type == TokenType.CLOSED &&
		   Factor.numTokensOfType(TokenType.OPENED) == 1 &&
		   Factor.numTokensOfType(TokenType.CLOSED) == 1 &&
		   Factor.numTokensOfType(TokenType.POW) == 0)
		{
			Factor.remove(0);
			Factor.remove(Factor.Length() - 1);
		}
		
		
		List<TokenList> ResultList = new ArrayList<TokenList>();
		
		int numOpened = 0; int numClosed = 0;
		
		TokenList CurrentList = new TokenList();
		
		if(!isLinear(Factor))
		{
			for(int i = 0; i < Factor.Length(); i++)
			{
				switch(Factor.tokenAt(i).type)
				{
				case OPENED:
					numOpened++;
					CurrentList.addToken(Factor.tokenAt(i));
					break;
				case CLOSED:
					numClosed++;
					CurrentList.addToken(Factor.tokenAt(i));
					break;
				case POW:
					if(numOpened == numClosed)
					{
						ResultList.add(CurrentList);
						CurrentList = new TokenList();
					}
					else CurrentList.addToken(Factor.tokenAt(i));
					break;
				default:
					CurrentList.addToken(Factor.tokenAt(i));
					break;
				}
			}
			if(numOpened < numClosed) 
				Error.showError("Number of closed brackets is bigger than the number of opened brackets!");
			if(numOpened > numClosed) 
				Error.showError("Number of opened brackets is bigger than the number of closed brackets!");
			
			if(numOpened == numClosed && CurrentList.Length() != 0)
				ResultList.add(CurrentList);
		}
		else ResultList.add(Factor);
		
		List<Power> Powers = new ArrayList<Power>();
		for(int i = 0; i < ResultList.size(); i++)
		{
			Powers.add(ParsePower(ResultList.get(i)));
		}
		
		return new Factor(Powers);
	} 

	private Power ParsePower(TokenList Power) 
	{
		if(Power.tokenAt(0).type == TokenType.OPENED && 
		   Power.tokenAt(Power.Length() - 1).type == TokenType.CLOSED &&
		   Power.numTokensOfType(TokenType.OPENED) == 1 &&
		   Power.numTokensOfType(TokenType.CLOSED) == 1)
		{
			Power.remove(0);
			Power.remove(Power.Length() - 1);
		}
		
		if(isLinear(Power)) 
		{
			switch(Power.Length())
			{
			case 1:
				if(Power.tokenAt(0).type == TokenType.X) 
					return new Power(new Linear(1.0,1.0));
				else if(Power.tokenAt(0).type == TokenType.NUMBER) 
					return new Power(new Linear(Double.parseDouble(Power.tokenAt(0).value), 0.0));			
				else 
					return new Power(new Linear());
			case 2:
				return new Power(new Linear(Double.parseDouble(Power.tokenAt(0).value), 1.0));
			case 3:
				if(Power.tokenAt(0).type == TokenType.NUMBER && 
				   Power.tokenAt(1).type == TokenType.TIMES &&
				   Power.tokenAt(2).type == TokenType.X)
					return new Power(new Linear(Double.parseDouble(Power.tokenAt(0).value), 
								  			 	 1.0));
				if(Power.tokenAt(0).type == TokenType.X && 
				   Power.tokenAt(1).type == TokenType.POW &&
				   Power.tokenAt(2).type == TokenType.NUMBER)
					return new Power(new Linear(1.0, 
												 Double.parseDouble(Power.tokenAt(2).value)));
				if(Power.tokenAt(0).type == TokenType.NUMBER && 
				   Power.tokenAt(1).type == TokenType.POW &&
				   Power.tokenAt(2).type == TokenType.NUMBER)
					return new Power(new Linear(Double.parseDouble(Power.tokenAt(0).value), 
							 					 Double.parseDouble(Power.tokenAt(2).value)));
			case 4:
				return new Power(new Linear(Double.parseDouble(Power.tokenAt(0).value), 
	 					 				     Double.parseDouble(Power.tokenAt(3).value)));
			case 5:
				return new Power(new Linear(Double.parseDouble(Power.tokenAt(0).value), 
	 				     					 Double.parseDouble(Power.tokenAt(4).value)));
			default:
				return new Power(new Linear());
			}
		}			
		else if(TokenType.FUNCTIONS.contains(Power.tokenAt(0).type) || Power.tokenAt(0).type == TokenType.NAME)
		{
			Function function;
			switch(Power.tokenAt(0).type)
			{
			case SIN:
				function = new Sin();
				break;
			case COS:
				function = new Cos();
				break;
			case ABS:
				function = new Abs();
				break;
			case CEILING:
				function = new Ceiling();
				break;
			case TAN:
				function = new Tan();
				break;
			case CTG:
				function = new Ctg();
				break;
			case LOG:
				function = new Log();
				break;
			case SQRT:
				function = new Sqrt();
				break;
			case FLOOR:
				function = new Floor();
				break;
			case NAME:
				for(int i = 0; i < CustomFunctions.expressions.size(); i++)
				{
					if(CustomFunctions.expressions.get(i).getL().equals(Power.tokenAt(0).value))
					{
						Power.remove(0);
						TokenList copiedTokens = new TokenList();
						for(int j = 0; j < CustomFunctions.expressions.get(i).getM().Length(); j++)
							copiedTokens.addToken(CustomFunctions.expressions.get(i).getM().tokenAt(j));
						TokenList newTokens = substituteTokens(Power, copiedTokens);
						return new Power(new Function(), ParseExpression(newTokens));
					}
				}
				
				Error.showError("You used a custom function that you have not yet defined!");		
				function = new Function();
				break;
			default:
				function = new Function();
				break;
			}
			
			Power.remove(0);
			
			if(Power.tokenAt(0).type == TokenType.OPENED && Power.tokenAt(Power.Length() - 1).type == TokenType.CLOSED)
			{
				Power.remove(0);
				Power.remove(Power.Length() - 1);
			}
			
			return new Power(function, ParseExpression(Power));
		}
		
		if(Power.tokenAt(0).type == TokenType.OPENED && 
		   Power.tokenAt(Power.Length() - 1).type == TokenType.CLOSED &&
		   shouldRemoveBrackets(Power))
		{
			Power.remove(0);
			Power.remove(Power.Length() - 1);
		}
		
		return new Power(new Function(), ParseExpression(Power));
	}

	private TokenList substituteTokens(TokenList factor, TokenList knownExpression) 
	{
		for(int i = 0; i < knownExpression.Length(); i++)
		{
			if(knownExpression.tokenAt(i).type == TokenType.X)
			{
				knownExpression.remove(i);
				knownExpression.addAll(i, factor.getList());
				if(i > 0)
					if(knownExpression.tokenAt(i-1).type == TokenType.X || knownExpression.tokenAt(i-1).type == TokenType.NUMBER)
						knownExpression.insert(i, new Token(TokenType.TIMES));
				i += factor.getList().size() - 1;
			}
		}
		return knownExpression;
	}

	private boolean shouldRemoveBrackets(TokenList factor) 
	{
		int numOpened = 0; int numClosed = 0;
		for(int i = 0; i < factor.Length(); i++)
		{
			if(factor.tokenAt(i).type == TokenType.OPENED) numOpened++;
			else if(factor.tokenAt(i).type == TokenType.CLOSED) numClosed++;
			if(numClosed == numOpened && factor.tokenAt(i).type == TokenType.CLOSED)
			{
				if(i == factor.Length() - 1) return true;
				else return false;
			}
		}
		return false;
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

