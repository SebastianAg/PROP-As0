package inlupp1;

//Authors: Fredrik Sander & Sebastian Agfemalm

public class StringUtil {
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public static String appendTabs(String str, int tabs)
    {
        for (int i = 0; i < tabs; i++)
            str += "\t";

        return str;
    }

    public static String prependTabs(String str, int tabs)
    {
        String newString = "";

        for (int i = 0; i < tabs; i++)
            newString += "\t";

        newString += str;

        return newString;
    }
}
