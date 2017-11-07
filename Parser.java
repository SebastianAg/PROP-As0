package inlupp1;

import java.io.IOException;
import java.util.ArrayList;

public class Parser implements IParser{

	private Tokenizer tokenizer = null;
	private ArrayList<Lexeme> lexemes = null;

	public Parser()
	{
		tokenizer = new Tokenizer();
		lexemes = new ArrayList<Lexeme>();
	}

	@Override
	public void open(String fileName) throws IOException, TokenizerException {
		tokenizer.open(fileName);
	}

	@Override
	public INode parse() throws IOException, TokenizerException, ParserException {
		if (tokenizer == null)
			throw new ParserException("Uninitialized tokenizer");

		readLexemes();

		return null;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	private void readLexemes() throws IOException, TokenizerException {
		/*while (tokenizer.current() != null)
		{
			lexemes.add(tokenizer.current());
			tokenizer.moveNext();
		}*/
	}
}
