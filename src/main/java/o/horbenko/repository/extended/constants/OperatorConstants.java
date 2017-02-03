package o.horbenko.repository.extended.constants;


import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public final class OperatorConstants {

    public static final String REGEXP_CONTAINS_OPERATOR = "%";
    public static final String INNER_OBJECT_PATH_DELIMITER = Pattern.quote(".");

    //    GROUP OPERATORS
    public static final String INNER_OBJECT_START = "(";
    public static final String INNER_OBJECT_END = ")";

    //    LOGIC
    public static final String AND = "and";
    public static final String OR = "or";

    //    PRIMITVE or STRING
    public static final String EQ = "=";
    public static final String LESS_GREATER = "<>";
    public static final String NOT_EQ = "!=";

    //    PRIMITIVE
    public static final String GR_EQ = ">=";
    public static final String GR = ">";

    public static final String LS_EQ = "<=";
    public static final String LS = "<";

    //STRINGS
    public static final String CONTAINS = "contains";
    public static final String NOT_CONTAINS = "notcontains";
    public static final String STARTS_WITH = "startswith";
    public static final String ENDS_WITH = "endswith";
    public static final String LIKE = "like";

    public static final Set<String> LOGIC_OPERATORS;

    static {
        LOGIC_OPERATORS = new HashSet<>();
        LOGIC_OPERATORS.add(AND);
        LOGIC_OPERATORS.add(OR);
    }

}
