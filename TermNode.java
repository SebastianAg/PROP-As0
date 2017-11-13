package inlupp1;

import java.util.ArrayList;

public class TermNode implements INode {

    //Must exist
    public FactorNode factor;

    //Can exist
    public Lexeme operator;
    public TermNode term;

    @Override
    public Object evaluate(Object[] args) throws Exception {
        if (factor != null && operator == null && term == null)
            return factor.evaluate(args);

        if (factor != null && operator != null && term != null)
        {
            switch (operator.token())
            {
                case ADD_OP:
                    return factor.evaluate(args) + " + " + term.evaluate(args);
                case SUB_OP:
                    return factor.evaluate(args) + " - " + term.evaluate(args);
                case DIV_OP:
                    return factor.evaluate(args) + " / " + term.evaluate(args);
                case MULT_OP:
                    return factor.evaluate(args) + " * " + term.evaluate(args);
                default:
                    break;
            }
        }

        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append(StringUtil.prependTabs("TermNode\n", tabs));
        factor.buildString(builder, tabs + 1);

        if (operator != null && term != null)
        {
            builder.append(StringUtil.prependTabs(operator.toString() + "\n", tabs + 1));
            term.buildString(builder, tabs + 1);
        }
    }
}

