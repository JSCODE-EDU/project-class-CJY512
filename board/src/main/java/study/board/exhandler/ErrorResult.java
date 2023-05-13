package study.board.exhandler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResult<T> {

    private final String errorMessage;
    private final String errorCode;
    private T errors;
}
