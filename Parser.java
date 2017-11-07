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

		TermNode termNode = termRule();
		if (termNode != null)
			node.term = termNode;

		Token currentToken = lexemes.get(activeLexemeIndex).token();
		if (currentToken == Token.ADD_OP || currentToken == Token.SUB_OP)
		{
			int lexemeIndexForOP = activeLexemeIndex;

			ExpressionNode expressionNode = expressionRule();

			if (expressionNode != null)
			{
				node.operator = lexemes.get(lexemeIndexForOP);
				node.expression = expressionNode;
			}
		}

		if (node.term == null)
			return null;

		return node;
	}

	private TermNode termRule()
	{
		TermNode node = new TermNode();

		FactorNode factorNode = factorRule();
		if (factorNode != null)
			node.factor = factorNode;

		Token currentToken = lexemes.get(activeLexemeIndex).token();
		if ( currentToken == Token.MULT_OP || currentToken == Token.DIV_OP)
		{
			int lexemeIndexForOp = activeLexemeIndex;

			TermNode termNode = termRule();

			if (termNode != null)
			{
				node.operator = lexemes.get(lexemeIndexForOp);
				node.term = termNode;
			}
		}

		if (node.factor != null)
			return node;

		return null;
	}

	private FactorNode factorRule()
	{
		FactorNode node = new FactorNode();

		if (lexemes.get(activeLexemeIndex).token() == Token.INT_LIT)
		{
			node.integer = lexemes.get(activeLexemeIndex);
			activeLexemeIndex++;
			return node;
		} else if (lexemes.get(activeLexemeIndex).token() == Token.LEFT_PAREN) {
			node.leftParantesis = lexemes.get(activeLexemeIndex);
			node.expression = expressionRule();
			node.rightParantesis = lexemes.get(activeLexemeIndex);
			return node;
		}

		return null;
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
