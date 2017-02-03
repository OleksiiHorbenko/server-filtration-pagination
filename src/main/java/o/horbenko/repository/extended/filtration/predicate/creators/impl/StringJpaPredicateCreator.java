package o.horbenko.repository.extended.filtration.predicate.creators.impl;

import o.horbenko.repository.extended.containers.FilterCriteriaHolder;
import o.horbenko.repository.extended.exceptions.predicate.PredicateCreationException;
import o.horbenko.repository.extended.filtration.predicate.creators.AbstractJpaPredicateCreator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import static o.horbenko.repository.extended.constants.OperatorConstants.*;
import static o.horbenko.repository.extended.exceptions.ErrMessageConstants.ERR_CANT_CREATE_PREDICATE;
import static o.horbenko.repository.extended.exceptions.ErrMessageConstants.ERR_UNKNOWN_OPERATOR;

public class StringJpaPredicateCreator extends AbstractJpaPredicateCreator<String> {

    private static Predicate startsWith(Path<String> root,
                                        CriteriaBuilder criteriaBuilder,
                                        FilterCriteriaHolder filterCriteriaHolder) {
        return criteriaBuilder.like(
            root,
            filterCriteriaHolder.getValue() + REGEXP_CONTAINS_OPERATOR
        );
    }

    private static Predicate endsWith(Path<String> root,
                                      CriteriaBuilder criteriaBuilder,
                                      FilterCriteriaHolder filterCriteriaHolder) {
        return criteriaBuilder.like(
            root,
            REGEXP_CONTAINS_OPERATOR + filterCriteriaHolder.getValue()
        );
    }

    private static Predicate notContains(Path<String> root,
                                         CriteriaBuilder criteriaBuilder,
                                         FilterCriteriaHolder filterCriteriaHolder) {
        return criteriaBuilder.notLike(
            root,
            new StringBuilder(REGEXP_CONTAINS_OPERATOR)
            .append(filterCriteriaHolder.getValue())
            .append(REGEXP_CONTAINS_OPERATOR).toString()
        );
    }

    private static Predicate like(Path<String> root,
                                         CriteriaBuilder criteriaBuilder,
                                         FilterCriteriaHolder filterCriteriaHolder) {
        return criteriaBuilder.like(
            root,
            new StringBuilder(REGEXP_CONTAINS_OPERATOR)
                .append(filterCriteriaHolder.getValue())
                .append(REGEXP_CONTAINS_OPERATOR).toString()
        );
    }


    @Override
    public Predicate createPredicate(FilterCriteriaHolder filterCriteriaHolder,
                                     Path<String> root,
                                     CriteriaBuilder criteriaBuilder) {
        try {

            String operator = filterCriteriaHolder.getOperator().trim();

            switch (operator) {

                case EQ:
                    return like(root, criteriaBuilder, filterCriteriaHolder);
                case LESS_GREATER:
                    return notContains(root, criteriaBuilder, filterCriteriaHolder);
                case NOT_EQ:
                    return notContains(root, criteriaBuilder, filterCriteriaHolder);
                case NOT_CONTAINS:
                    return notContains(root, criteriaBuilder, filterCriteriaHolder);


                case STARTS_WITH:
                    return startsWith(root, criteriaBuilder, filterCriteriaHolder);
                case ENDS_WITH:
                    return endsWith(root, criteriaBuilder, filterCriteriaHolder);
                case CONTAINS:
                    return like(root, criteriaBuilder, filterCriteriaHolder);
                case LIKE:
                    return like(root, criteriaBuilder, filterCriteriaHolder);

                    default:
                        throw new PredicateCreationException(ERR_UNKNOWN_OPERATOR, operator);
            }


        } catch (PredicateCreationException e) {
            throw new PredicateCreationException(ERR_CANT_CREATE_PREDICATE, e);
        }
    }

    @Override
    public String convertToFieldType(Object toConvert) {
        return toConvert.toString();
    }
}
