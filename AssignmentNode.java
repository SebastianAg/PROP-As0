package inlupp1;

public class AssignmentNode implements INode {

    private INode parentNode;

    @Override
    public Object evaluate(Object[] args) throws Exception {
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {

    }

    @Override
    public void setParent(INode newParent) {
        parentNode = newParent;
    }

    @Override
    public INode getParent() {
        return parentNode;
    }
}
