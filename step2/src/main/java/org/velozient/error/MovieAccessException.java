package org.velozient.error;

public class MovieAccessException extends  RuntimeException{
    public MovieAccessException() {
        super();
    }

    public MovieAccessException(String message) {
        super(message);
    }

    public MovieAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieAccessException(Throwable cause) {
        super(cause);
    }

    protected MovieAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
