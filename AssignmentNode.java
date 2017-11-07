package inlupp1;

public class AssignmentNode implements INode {

    //Must exist
    public Lexeme id;
    public Lexeme assignment;
    public ExpressionNode expression;
    public Lexeme semicolon;

    @Override
    public Object evaluate(Object[] args) /*throws Exception*/ {
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append(StringUtil.prependTabs("AssignmentNode\n", tabs));
        builder.append(StringUtil.prependTabs(id.toString() + "\n", tabs + 1));
        builder.append(StringUtil.prependTabs(assignment.toString() + "\n", tabs + 1));
        expression.buildString(builder, tabs + 1);
        builder.append(StringUtil.prependTabs(semicolon.toString() + "\n", tabs + 1));
    }
}
