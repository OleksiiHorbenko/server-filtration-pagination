package o.horbenko.repository.extended.exceptions.predicate;

import o.horbenko.repository.extended.exceptions.AbstractFetchException;

public class UnknownPredicateOperatorException extends AbstractFetchException {

    public UnknownPredicateOperatorException(Object... messageParts) {
        super(messageParts);
    }
}
