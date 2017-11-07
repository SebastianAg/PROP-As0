package inlupp1;

public class StatementsNode implements INode {

    //Can have both or be empty
    public AssignmentNode assignment;
    public StatementsNode statements;

    @Override
    public Object evaluate(Object[] args) {
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append(StringUtil.prependTabs("StatementsNode\n", tabs));

        if (assignment != null && statements != null)
        {
            assignment.buildString(builder, tabs + 1);
            statements.buildString(builder, tabs + 1);
        }
    }
}
