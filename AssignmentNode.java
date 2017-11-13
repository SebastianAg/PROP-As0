package inlupp1;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class AssignmentNode implements INode {

    //Must exist
    public Lexeme id;
    public Lexeme assignment;
    public ExpressionNode expression;
    public Lexeme semicolon;

    @Override
    public Object evaluate(Object[] args) /*throws Exception*/ {
        String evaluation = (String)expression.evaluate(args);

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        double value = 0;
        try {
           value = (double)engine.eval(evaluation);
        } catch (ScriptException e) {
            return null;
        }

        Evaluator.addValue((String)id.value(), value);
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
