package study.board.exhandler.advice;

public enum GlobalErrorCode {
    INTERNAL_SERVER("000","내부 오류"),
    NOT_FOUND("001","존재하지 않는 데이터입니다."),
    VALIDATION("002","validation error"),
    QUERY_VALIDATION("003","query validation error");

    private final String code;
    private final String message;

    GlobalErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return message;
    }
}
