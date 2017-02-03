package o.horbenko.repository.extended.utils;

import o.horbenko.repository.extended.containers.FilterCriteriaHolder;
import o.horbenko.repository.extended.exceptions.predicate.PredicateCreationException;
import o.horbenko.repository.extended.filtration.predicate.JpaPredicateCreatorFactory;
import o.horbenko.repository.extended.filtration.predicate.creators.AbstractJpaPredicateCreator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

import static o.horbenko.repository.extended.constants.OperatorConstants.INNER_OBJECT_PATH_DELIMITER;
import static o.horbenko.repository.extended.exceptions.ErrMessageConstants.ERR_CANT_CREATE_PREDICATE;

public class JpaPredicateutils {

    public static <DomainType> Predicate createPredicate(FilterCriteriaHolder filterCriteriaHolder,
                                                         Root<DomainType> root,
                                                         CriteriaBuilder criteriaBuilder) {
        try {

            Path<?> declaredTarget = getLastPathInChain(filterCriteriaHolder, root);

            AbstractJpaPredicateCreator predicateCreator =
                JpaPredicateCreatorFactory
                    .getSuitablePredicateCreator(declaredTarget.getJavaType());

            return predicateCreator.createPredicate(filterCriteriaHolder, declaredTarget, criteriaBuilder);


        } catch (Exception exception) {
            throw new PredicateCreationException(exception, ERR_CANT_CREATE_PREDICATE, filterCriteriaHolder.getOperator());
        }
    }

    private static <DomainType> Path<?> getLastPathInChain(FilterCriteriaHolder filterCriteriaHolder,
                                                           Root<DomainType> root) {

        Queue<String> queue = chunkFieldName(filterCriteriaHolder.getField());
        Path<?> mainPath = root;

        while(!queue.isEmpty())
            mainPath = mainPath.get(queue.poll());

        return mainPath;
    }

    private static Queue<String> chunkFieldName(String field) {
        Queue<String> result = new ArrayDeque<>();

        String[] chunkedFieldName = field.split(INNER_OBJECT_PATH_DELIMITER);

        Collections.addAll(result, chunkedFieldName);
        return result;
    }


}
