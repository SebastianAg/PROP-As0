package inlupp1;

import java.io.IOException;
import java.util.ArrayList;

public class Parser implements IParser{

	private Tokenizer tokenizer = null;
	private ArrayList<Lexeme> lexemes = null;
	private INode rootNode;

	private int activeLexemeIndex = 0;

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
		System.out.println("Parsing");

		if (tokenizer == null)
			throw new ParserException("Uninitialized tokenizer");

		//Works as intended
		lexemes = readLexemes();

		System.out.println("Printing lexemes");
		for (Lexeme lexeme : lexemes)
			System.out.print(lexeme);
		System.out.println();

		parseLexemes();

		return rootNode;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	private void parseLexemes()
	{
		activeLexemeIndex = 0;

		for (int i = 0; i < lexemes.size(); i++)
		{
			if (lexemes.get(i).token() == Token.ASSIGN_OP)
			{
				activeLexemeIndex = i;
				AssignmentNode node = assignmentRule();
				rootNode = node;
			}
		}
	}

	private AssignmentNode assignmentRule()
	{
		AssignmentNode node = new AssignmentNode();
		node.id = lexemes.get(activeLexemeIndex - 1); //Left of =, check so it's actually a identifier
		node.assignment = lexemes.get(activeLexemeIndex);
		activeLexemeIndex++;
		node.expression = expressionRule();
		node.semicolon = lexemes.get(lexemes.size() - 1); //Fix later

		return node;
	}

	private ExpressionNode expressionRule()
	{
		ExpressionNode node = new ExpressionNode();

		node.term = termRule();

		return node;
	}

	private TermNode termRule()
	{
		TermNode node = new TermNode();

		node.factor = factorRule();

		return node;
	}

	private FactorNode factorRule()
	{
		FactorNode node = new FactorNode();

		node.integer = lexemes.get(activeLexemeIndex);
		activeLexemeIndex++;

		return node;
	}

	private ArrayList<Lexeme> readLexemes() throws IOException, TokenizerException {
		ArrayList<Lexeme> readLexemes = new ArrayList<Lexeme>();

		tokenizer.moveNext();
		while (tokenizer.current() != null && tokenizer.current().token() != Token.EOF) {
			readLexemes.add(tokenizer.current());
			tokenizer.moveNext();
		}

		return readLexemes;
	}
}
