package study.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "error.board.notAuthorized")
public class BoardNotAuthorizedException extends RuntimeException {

//    private final static String MESSAGE = ""
    public BoardNotAuthorizedException() {
        super();
    }

    public BoardNotAuthorizedException(String message) {
        super(message);
    }

    public BoardNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardNotAuthorizedException(Throwable cause) {
        super(cause);
    }

    protected BoardNotAuthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
