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

        if (term != null && operator == null && expression == null)
            return term.evaluate(args);

        if (term != null && operator != null && expression != null)
        {
            switch (operator.token())
            {
                case ADD_OP:
                    return (double)term.evaluate(args) + (double)expression.evaluate(args);
                case SUB_OP:
                    return (double)term.evaluate(args) - (double)expression.evaluate(args);
                case DIV_OP:
                    return (double)term.evaluate(args) / (double)expression.evaluate(args);
                case MULT_OP:
                    return (double)term.evaluate(args) * (double)expression.evaluate(args);
                default:
                    break;
            }
        }

        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append(StringUtil.prependTabs("ExpressionNode\n", tabs));
        term.buildString(builder, tabs + 1);

        if (operator != null && expression != null)
        {
            builder.append(StringUtil.prependTabs(operator.toString() + "\n", tabs + 1));
            expression.buildString(builder, tabs + 1);
        }
    }
}

