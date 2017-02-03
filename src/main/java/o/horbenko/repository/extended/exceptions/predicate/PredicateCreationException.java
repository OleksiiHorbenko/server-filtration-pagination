package o.horbenko.repository.extended.exceptions.predicate;

import o.horbenko.repository.extended.exceptions.AbstractFetchException;

public class PredicateCreationException extends AbstractFetchException {

    public PredicateCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PredicateCreationException(Throwable cause, Object... messageParts) {
        super(cause, messageParts);
    }

    public PredicateCreationException(Object... messageParts) {
        super(messageParts);
    }
}
