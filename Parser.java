package inlupp1;

import java.io.IOException;
import java.util.ArrayList;

public class Parser implements IParser{

	private Tokenizer tokenizer = null;
	private ArrayList<Lexeme> lexemes = null;
	private INode rootNode;

	private int activeLexemeIndex = -1;

	private int startBlockIndex = -1;
	private int endBlockIndex = - 1;

	private int lastAssignOpIndex = -1;

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

		lexemes = readLexemes();
		parseLexemes();

		return rootNode;
	}

	@Override
	public void close() throws IOException {
		tokenizer.close();
	}

	private void parseLexemes()
	{
		for (int i = 0; i < lexemes.size(); i++)
		{
			if (lexemes.get(i).token() == Token.LEFT_CURLY)
			{
				startBlockIndex = i;
				activeLexemeIndex = i;
			}

			if (activeLexemeIndex != -1 && lexemes.get(i).token() == Token.RIGHT_CURLY)
			{
				endBlockIndex = i;
				BlockNode node = blockRule();
				rootNode = node;
			}
		}
	}

	private BlockNode blockRule()
	{
		BlockNode node = new BlockNode();

		node.leftCurly = lexemes.get(startBlockIndex);
		node.statements = statementsRule();
		node.rightCurly = lexemes.get(endBlockIndex);

		return node;
	}

	private StatementsNode statementsRule()
	{
		StatementsNode node = new StatementsNode();

		AssignmentNode assignment = assignmentRule();

		//node.assignment = assignment;
		StatementsNode statements = null;
		if (assignment != null)
			statements = statementsRule();

		if (assignment != null && statements != null)
		{
			node.assignment = assignment;
			node.statements = statements;
		}
		else
			return node;

		return node;
	}

	//In every case that we return null, there is no occurring assignmentRule.
	private AssignmentNode assignmentRule()
	{
		AssignmentNode node = new AssignmentNode();

		//We need to keep track of the last ASSIGN_OP, to know where to search for the next one.
		int assignOPIndex = -1;
		int startIndex = lastAssignOpIndex != -1 ? lastAssignOpIndex + 1 : startBlockIndex;
		for (int i = startIndex; i < endBlockIndex; i++)
		{
			if (assignOPIndex == -1 && lexemes.get(i).token() == Token.ASSIGN_OP)
			{
				assignOPIndex = i;
				activeLexemeIndex = assignOPIndex;
			}
		}

		//If we have not found a ASSIGN_OP.
		if (assignOPIndex == -1)
			return null;
		else
			lastAssignOpIndex = assignOPIndex;

		//Left of =, check so it's actually a identifier
		if (lexemes.get(assignOPIndex - 1).token() == Token.IDENT)
			node.id = lexemes.get(assignOPIndex - 1);
		else
			return null;

		//We have already checked that the index represents a Token.ASSIGN_OP lexeme
		node.assignment = lexemes.get(assignOPIndex);
		activeLexemeIndex++;
		node.expression = expressionRule();

		//Check so it's actually lexeme of class SEMICOLON
		int semiColonIndex = -1;
		for (int i = startIndex; i < endBlockIndex; i++)
			if (semiColonIndex == -1 && lexemes.get(i).token() == Token.SEMICOLON)
				semiColonIndex = i;

		if (lexemes.get(semiColonIndex).token() == Token.SEMICOLON )
			node.semicolon = lexemes.get(semiColonIndex);
		else
			return null;

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
			activeLexemeIndex++;

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
		if (currentToken == Token.MULT_OP || currentToken == Token.DIV_OP)
		{
			int lexemeIndexForOp = activeLexemeIndex;
			activeLexemeIndex++;

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

		if (lexemes.get(activeLexemeIndex).token() == Token.INT_LIT) {
			node.integer = lexemes.get(activeLexemeIndex);
			activeLexemeIndex++;
			return node;
		} else if (lexemes.get(activeLexemeIndex).token() == Token.IDENT) {
			node.id = lexemes.get(activeLexemeIndex);
			activeLexemeIndex++;
			return node;
		} else if (lexemes.get(activeLexemeIndex).token() == Token.LEFT_PAREN) {
			node.leftParantesis = lexemes.get(activeLexemeIndex);
			activeLexemeIndex++;
			node.expression = expressionRule();
			node.rightParantesis = lexemes.get(activeLexemeIndex);
			activeLexemeIndex++;
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
