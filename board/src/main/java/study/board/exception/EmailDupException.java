package study.board.exception;

public class EmailDupException extends RuntimeException {
    public EmailDupException() {
        super();
    }

    public EmailDupException(String message) {
        super(message);
    }

    public EmailDupException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailDupException(Throwable cause) {
        super(cause);
    }

    protected EmailDupException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
