package o.horbenko.repository.extended.filtration.predicate.creators;

import o.horbenko.repository.extended.containers.FilterCriteriaHolder;
import o.horbenko.repository.extended.exceptions.predicate.PredicateCreationException;
import o.horbenko.repository.extended.filtration.predicate.JpaComparisonOperatorMapWrapper;
import org.hibernate.jpa.criteria.CriteriaBuilderImpl;
import org.hibernate.jpa.criteria.predicate.ComparisonPredicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import static o.horbenko.repository.extended.exceptions.ErrMessageConstants.ERR_CANT_CREATE_PREDICATE;

public abstract class AbstractJpaPredicateCreator<T> {

    public Predicate createPredicate(FilterCriteriaHolder filterCriteriaHolder,
                                     Path<T> declaredTarget,
                                     CriteriaBuilder criteriaBuilder) {
        try {

            String operator = filterCriteriaHolder.getOperator().trim();
            T value = convertToFieldType(filterCriteriaHolder.getValue());

            ComparisonPredicate.ComparisonOperator comparisonOperator =
                JpaComparisonOperatorMapWrapper.getOperatorOrThrow(operator);

            return new ComparisonPredicate(
                (CriteriaBuilderImpl) criteriaBuilder,
                comparisonOperator,
                declaredTarget,
                value
            );


        } catch (Exception e) {
            throw new PredicateCreationException(ERR_CANT_CREATE_PREDICATE, e);
        }
    }

    public abstract T convertToFieldType(Object toConvert);


}
