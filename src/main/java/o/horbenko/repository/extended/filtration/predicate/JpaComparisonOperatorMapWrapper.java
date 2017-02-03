package o.horbenko.repository.extended.filtration.predicate;

import o.horbenko.repository.extended.exceptions.predicate.UnknownPredicateOperatorException;
import org.hibernate.jpa.criteria.predicate.ComparisonPredicate;

import java.util.HashMap;
import java.util.Map;

import static o.horbenko.repository.extended.constants.OperatorConstants.*;
import static o.horbenko.repository.extended.exceptions.ErrMessageConstants.ERR_CANT_RESOLVE_PREDICATE_OPERATOR;

public class JpaComparisonOperatorMapWrapper {

    private static final Map<String, ComparisonPredicate.ComparisonOperator> COMPARISON_OPERATOR_MAP;

    static {
        COMPARISON_OPERATOR_MAP = new HashMap<>();

        COMPARISON_OPERATOR_MAP.put(EQ, ComparisonPredicate.ComparisonOperator.EQUAL);

        COMPARISON_OPERATOR_MAP.put(GR_EQ, ComparisonPredicate.ComparisonOperator.GREATER_THAN_OR_EQUAL);
        COMPARISON_OPERATOR_MAP.put(LS_EQ, ComparisonPredicate.ComparisonOperator.LESS_THAN_OR_EQUAL);

        COMPARISON_OPERATOR_MAP.put(GR, ComparisonPredicate.ComparisonOperator.GREATER_THAN);
        COMPARISON_OPERATOR_MAP.put(LS, ComparisonPredicate.ComparisonOperator.LESS_THAN);

        COMPARISON_OPERATOR_MAP.put(LESS_GREATER, ComparisonPredicate.ComparisonOperator.NOT_EQUAL);
        COMPARISON_OPERATOR_MAP.put(NOT_EQ, ComparisonPredicate.ComparisonOperator.NOT_EQUAL);
    }

    public static ComparisonPredicate.ComparisonOperator getOperatorOrThrow(String operator) {

        if (COMPARISON_OPERATOR_MAP.containsKey(operator))
            return COMPARISON_OPERATOR_MAP.get(operator);

        throw new UnknownPredicateOperatorException(ERR_CANT_RESOLVE_PREDICATE_OPERATOR, operator);
    }

}
