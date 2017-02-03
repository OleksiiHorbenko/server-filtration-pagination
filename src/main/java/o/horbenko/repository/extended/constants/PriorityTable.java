package o.horbenko.repository.extended.constants;

import java.util.HashMap;
import java.util.Map;

import static o.horbenko.repository.extended.constants.OperatorConstants.*;

public final class PriorityTable {

    private static final Map<String, Integer> PRIORITY_MAP;
    private static final int DEFAULT_PRIORITY = 10;
    private static final int FINAL_OPERATION_PRIORITY = 3;

    static {
        PRIORITY_MAP = new HashMap<>();
        PRIORITY_MAP.put(INNER_OBJECT_START, 0);
        PRIORITY_MAP.put(INNER_OBJECT_END, 0);

        PRIORITY_MAP.put(OR, 1);
        PRIORITY_MAP.put(AND, 2);

        PRIORITY_MAP.put(EQ, FINAL_OPERATION_PRIORITY);
        PRIORITY_MAP.put(LESS_GREATER, FINAL_OPERATION_PRIORITY);
        PRIORITY_MAP.put(NOT_EQ, FINAL_OPERATION_PRIORITY);
        PRIORITY_MAP.put(GR_EQ, FINAL_OPERATION_PRIORITY);
        PRIORITY_MAP.put(GR_EQ, FINAL_OPERATION_PRIORITY);
        PRIORITY_MAP.put(GR, FINAL_OPERATION_PRIORITY);
        PRIORITY_MAP.put(LS_EQ, FINAL_OPERATION_PRIORITY);
        PRIORITY_MAP.put(LS, FINAL_OPERATION_PRIORITY);
        PRIORITY_MAP.put(CONTAINS, FINAL_OPERATION_PRIORITY);
        PRIORITY_MAP.put(NOT_CONTAINS, FINAL_OPERATION_PRIORITY);
        PRIORITY_MAP.put(STARTS_WITH, FINAL_OPERATION_PRIORITY);
        PRIORITY_MAP.put(ENDS_WITH, FINAL_OPERATION_PRIORITY);
        PRIORITY_MAP.put(LIKE, FINAL_OPERATION_PRIORITY);
    }

    public static boolean isOperator(String lexem) {
        return PRIORITY_MAP.containsKey(lexem);
    }

    public static int getPriorityOf(String lexem) {
        return PRIORITY_MAP.getOrDefault(lexem, DEFAULT_PRIORITY);
    }

    public static int comparePriority(String first, String second) {
        return ((Integer) getPriorityOf(first)).compareTo(getPriorityOf(second));
    }

}
