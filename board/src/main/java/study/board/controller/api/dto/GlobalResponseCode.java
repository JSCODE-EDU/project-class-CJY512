package study.board.controller.api.dto;

public enum GlobalResponseCode {
    SUCCESS("000","ok"),
    SUCCESS_BOARD_LIST("001","board list"),
    SUCCESS_BOARD("002","board info"),
    SUCCESS_SAVE("003","saved successfully"),
    SUCCESS_UPDATE("004","updated successfully"),
    SUCCESS_MEMBER("005","member mypage");


    private final String code;
    private final String message;

    GlobalResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
