package inlupp1;

import java.util.ArrayList;

public class FactorNode implements INode {

    //Must exist
    public Lexeme integer;

    //Or must exist
    public Lexeme leftParantesis;
    public ExpressionNode expression;
    public Lexeme rightParantesis;

    @Override
    public Object evaluate(Object[] args) /*throws Exception*/ {
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append(StringUtil.prependTabs(integer.toString() + "\n", tabs + 1));
    }
}
