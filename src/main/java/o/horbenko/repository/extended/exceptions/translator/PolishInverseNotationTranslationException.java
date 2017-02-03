package o.horbenko.repository.extended.exceptions.translator;

import o.horbenko.repository.extended.exceptions.AbstractFetchException;

public class PolishInverseNotationTranslationException extends AbstractFetchException {

    public PolishInverseNotationTranslationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PolishInverseNotationTranslationException(String message) {
        super(message);
    }

    public PolishInverseNotationTranslationException(Object... messageParts) {
        super(messageParts);
    }
}
