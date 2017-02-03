package o.horbenko.repository.extended.filtration.predicate;

import o.horbenko.repository.extended.filtration.predicate.creators.AbstractJpaPredicateCreator;
import o.horbenko.repository.extended.filtration.predicate.creators.impl.DefaultJpaPredicateCreator;
import o.horbenko.repository.extended.filtration.predicate.creators.impl.InnerCollectionJpaPredicateCreator;
import o.horbenko.repository.extended.filtration.predicate.creators.impl.StringJpaPredicateCreator;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class JpaPredicateCreatorFactory {

    private static final AbstractJpaPredicateCreator<String> DEFAULT_JPA_PREDICATE_CREATOR;

    private static final AbstractJpaPredicateCreator<String> STRINGS_JPA_PREDICATE_CREATOR;
    private static final AbstractJpaPredicateCreator<Collection> INNER_COLLECTION_JPA_PREDICATE_CREATOR;

    static {
        DEFAULT_JPA_PREDICATE_CREATOR = new DefaultJpaPredicateCreator();
        STRINGS_JPA_PREDICATE_CREATOR = new StringJpaPredicateCreator();
        INNER_COLLECTION_JPA_PREDICATE_CREATOR = new InnerCollectionJpaPredicateCreator();
    }

    public static AbstractJpaPredicateCreator getSuitablePredicateCreator(Class<?> declaredValueType) {

        if (declaredValueType == Set.class || declaredValueType == List.class)
            return INNER_COLLECTION_JPA_PREDICATE_CREATOR;

        if (declaredValueType == String.class)
            return STRINGS_JPA_PREDICATE_CREATOR;

        return DEFAULT_JPA_PREDICATE_CREATOR;
    }


}
