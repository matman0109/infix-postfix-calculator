/*
------------------------
Author: Matvii Repetskyi
------------------------
 */

import java.util.HashMap;
import java.util.Map;

//creates enums for different operators in order to make precedence work
enum Operator {
    NOT("!", 5, Associativity.RIGHT),
    MULTIPLY("*", 4, Associativity.LEFT),
    DIVIDE("/", 4, Associativity.LEFT),
    ADD("+", 3, Associativity.LEFT),
    SUBTRACT("-", 3, Associativity.LEFT),
    LESS_THAN("<", 2, Associativity.LEFT),
    GREATER_THAN(">", 2, Associativity.LEFT),
    AND("&", 1, Associativity.LEFT),
    OR("|", 1, Associativity.LEFT),
    ASSIGN("=", 0, Associativity.RIGHT);


    public final String symbol;
    public final int precedence;
    public final Associativity associativity;

    //creates hashmap to put the operators together
    public static final Map<String, Operator> OPERATORS_MAP = new HashMap<>();

    static {
        for (Operator op : Operator.values()) {
            OPERATORS_MAP.put(op.symbol, op);
        }
    }

    Operator(String symbol, int precedence, Associativity associativity) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.associativity = associativity;
    }

    public int getPrecedence() {
        return precedence;
    }

    public Associativity getAssociativity() {
        return associativity;
    }

    public static boolean isOperator(String token) {
        return OPERATORS_MAP.containsKey(token);
    }

    public static Operator getOperator(String token) {
        return OPERATORS_MAP.get(token);
    }
}

enum Associativity {
    LEFT,
    RIGHT
}
