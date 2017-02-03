package o.horbenko.repository.extended.filtration.specification;

import o.horbenko.repository.extended.containers.FilterCriteriaHolder;
import o.horbenko.repository.extended.utils.JpaPredicateutils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CustomJpaSpecification<DomainType> implements Specification<DomainType>{

    private FilterCriteriaHolder filterCriteriaHolder;

    public CustomJpaSpecification(FilterCriteriaHolder filterCriteriaHolder) {
        this.filterCriteriaHolder = filterCriteriaHolder;
    }

    @Override
    public Predicate toPredicate(Root<DomainType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return JpaPredicateutils
            .createPredicate(filterCriteriaHolder, root, cb);
    }
}
