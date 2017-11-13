package inlupp1;

//Authors: Fredrik Sander & Sebastian Agfemalm

public class StatementsNode implements INode {

    //Can have both or be empty
    public AssignmentNode assignment;
    public StatementsNode statements;

    @Override
    public Object evaluate(Object[] args) throws Exception
    {
        if (assignment != null && statements != null)
        {
            assignment.evaluate(args);
            statements.evaluate(args);
        }

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
