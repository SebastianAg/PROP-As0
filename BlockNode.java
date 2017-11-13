package inlupp1;

//Authors: Fredrik Sander & Sebastian Agfemalm

public class BlockNode implements INode {

    public Lexeme leftCurly;
    public StatementsNode statements;
    public Lexeme rightCurly;

    @Override
    public Object evaluate(Object[] args) throws Exception
    {
        statements.evaluate(args);
        return Evaluator.buildString();
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("BlockNode\n");
        builder.append(StringUtil.prependTabs(leftCurly.toString() + "\n", tabs));
        statements.buildString(builder, tabs + 1);
        builder.append(StringUtil.prependTabs(rightCurly.toString() + "\n", tabs));
    }
}
