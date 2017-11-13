package inlupp1;

import java.util.HashMap;

public class PrecedenceLevel {
    private static HashMap<Token, Integer> levels = new HashMap<Token, Integer>();

    static
    {
        levels.put(Token.SUB_OP, 0);
        levels.put(Token.ADD_OP, 0);
        levels.put(Token.MULT_OP, 1);
        levels.put(Token.DIV_OP, 1);
        levels.put(Token.LEFT_PAREN, 2);
        levels.put(Token.RIGHT_PAREN, 2);
    }

    public static Integer get(Token token) { return levels.get(token); }
}
