package o.horbenko.repository.extended.exceptions.specification;

import o.horbenko.repository.extended.exceptions.AbstractFetchException;

public class PolishInverseNotationExecutionException extends AbstractFetchException {

    public PolishInverseNotationExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PolishInverseNotationExecutionException(String message) {
        super(message);
    }

    public PolishInverseNotationExecutionException(Object... messageParts) {
        super(messageParts);
    }
}
