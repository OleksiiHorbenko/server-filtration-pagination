package o.horbenko.repository.extended.filtration.predicate.creators.impl;

import o.horbenko.repository.extended.filtration.predicate.creators.AbstractJpaPredicateCreator;

public class DefaultJpaPredicateCreator extends AbstractJpaPredicateCreator<String> {

    @Override
    public String convertToFieldType(Object toConvert) {
        return toConvert.toString();
    }
}
