package o.horbenko.repository.extended.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractFetchException extends RuntimeException {
    public static final Logger LOG = LoggerFactory.getLogger(AbstractFetchException.class);

    protected AbstractFetchException(String message, Throwable cause) {
        super(message, cause);
        LOG.error(message, cause);
    }

    protected AbstractFetchException(String message) {
        super(message);
        LOG.error(message);
    }

    protected AbstractFetchException(Object... messageParts) {
        super(buildErrorMessage(messageParts));
        LOG.error(buildErrorMessage(messageParts));
    }

    private static String buildErrorMessage(Object... messageParts) {
        StringBuilder sb = new StringBuilder();

        for (Object part : messageParts)
            sb.append(part.toString());

        return sb.toString();
    }

}
