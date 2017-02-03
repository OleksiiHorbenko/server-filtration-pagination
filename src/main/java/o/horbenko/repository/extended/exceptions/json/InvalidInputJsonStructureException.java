package o.horbenko.repository.extended.exceptions.json;

import o.horbenko.repository.extended.exceptions.AbstractFetchException;

public class InvalidInputJsonStructureException extends AbstractFetchException {

    public InvalidInputJsonStructureException(String message, Throwable cause) {
        super(message, cause);
    }
}
