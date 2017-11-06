package inlupp1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;


public class Tokenizer implements ITokenizer{
	
	private static Map<Character, Token> symbols = null;
	
	private Scanner scanner = null;
	private Lexeme current = null;
	private Lexeme next = null;
	
	public Tokenizer() {
		symbols = new HashMap<Character, Token>();
		symbols.put(Scanner.EOF, Token.EOF);
		symbols.put('+', Token.ADD_OP);
		symbols.put('-', Token.SUB_OP);
		symbols.put('*', Token.MULT_OP);
		symbols.put('/', Token.DIV_OP);
		symbols.put('=', Token.ASSIGN_OP);
		symbols.put('(', Token.LEFT_PAREN);
		symbols.put(')', Token.RIGHT_PAREN);
		symbols.put(';', Token.SEMICOLON);
		//symbols.put('{', Token.LEFT_CURLY);
		//symbols.put('}', Token.RIGHT_CURLY);
		
		
	}
	@Override
	public void open(String fileName) throws IOException, TokenizerException {
		scanner = new Scanner();
		scanner.open(fileName);
		scanner.moveNext();
		next = extractLexeme();
		
	}
	
	 private void consumeWhiteSpaces() throws IOException {
		while (Character.isWhitespace(scanner.current())){
			scanner.moveNext();
		}
	 }
	 
	 private Lexeme extractLexeme() throws IOException, TokenizerException {
			consumeWhiteSpaces();

			Character ch = scanner.current();
			StringBuilder strBuilder = new StringBuilder();

			if (ch == Scanner.EOF)
			    return new Lexeme(ch, Token.EOF);
			else if (Character.isLetter(ch)) {
			    while (Character.isLetter(scanner.current())) {
			    	strBuilder.append(scanner.current());
			    	scanner.moveNext();
			    }
			    String lexeme = strBuilder.toString(); 
			    return new Lexeme (lexeme, Token.IDENT);
			    //System.out.println(lexeme);
			}
			else if (isNumeric(ch.toString())) {
				while (isNumeric(Character.toString(scanner.current()))) {
			    	strBuilder.append(scanner.current());
			    	scanner.moveNext();
			    }
				String lexeme = strBuilder.toString(); 
				return new Lexeme (lexeme, Token.INT_LIT);
			}
			else if (symbols.containsKey(ch)) {
			    scanner.moveNext();
			    return new Lexeme(ch, symbols.get(ch));
			}
			else
			    throw new TokenizerException("Unknown character: " + String.valueOf(ch));
		    }


	@Override
	public Lexeme current() {
		return current;
	}

	@Override
	public void moveNext() throws IOException, TokenizerException {
		if (scanner == null)
		    throw new IOException("No open file.");
		current = next;
		if (next.token() != Token.EOF)
		    next = extractLexeme();
	    
		
		
		
	}

	@Override
	public void close() throws IOException {
		scanner.close();
		
	}
	
	private boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

}