package o.horbenko.repository.extended.filtration.predicate.creators.impl;

import o.horbenko.repository.extended.containers.FilterCriteriaHolder;
import o.horbenko.repository.extended.filtration.predicate.creators.AbstractJpaPredicateCreator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class InnerCollectionJpaPredicateCreator extends AbstractJpaPredicateCreator {

    @Override
    public Predicate createPredicate(FilterCriteriaHolder filterCriteriaHolder, Path declaredTarget, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isNull(null);
    }

    @Override
    public Object convertToFieldType(Object toConvert) {
        return null;
    }
}
