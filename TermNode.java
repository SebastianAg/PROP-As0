package inlupp1;

import java.util.ArrayList;

public class TermNode implements INode {

    //Must exist
    public FactorNode factor;

    //Can exist
    public Lexeme operator;
    public TermNode term;

    @Override
    public Object evaluate(Object[] args) /*throws Exception*/ {
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        factor.buildString(builder, tabs + 1);
    }
}

