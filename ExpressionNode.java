package inlupp1;

import java.util.ArrayList;

public class ExpressionNode implements INode {

    //Must exist
    public TermNode term;

    //Can exist
    public Lexeme operator;
    public ExpressionNode expression;

    @Override
    public Object evaluate(Object[] args) /*throws Exception*/ {
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        term.buildString(builder, tabs + 1);

        if (operator != null && expression != null)
        {
            builder.append(StringUtil.prependTabs(operator.toString() + "\n", tabs + 1));
            expression.buildString(builder, tabs + 1);
        }
    }
}

