package org.velozient.error;

public class MovieExportException extends  RuntimeException{
    public MovieExportException() {
        super();
    }

    public MovieExportException(String message) {
        super(message);
    }

    public MovieExportException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieExportException(Throwable cause) {
        super(cause);
    }

    protected MovieExportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
