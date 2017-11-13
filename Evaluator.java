package inlupp1;

import java.util.HashMap;

public class Evaluator {
    private static HashMap<String, Double> valueMap = new HashMap<String, Double>();

    public static void addValue(String identifier, Double value)
    {
        if (valueMap.containsKey(identifier))
        {
            Double initialValue = valueMap.get(identifier);
            valueMap.replace(identifier, initialValue + value);
        }
        else
            valueMap.put(identifier, value);
    }

    public static double getValue(String identifier)
    {
        return valueMap.get(identifier);
    }

    public static String buildString()
    {
        String evaluatorStr = "";

        for (HashMap.Entry<String, Double> entry : valueMap.entrySet())
            evaluatorStr += entry.getKey() + " = " + entry.getValue().toString() + "\n";

        return evaluatorStr;
    }
}
