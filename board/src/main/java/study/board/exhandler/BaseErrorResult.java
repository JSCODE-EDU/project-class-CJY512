package study.board.exhandler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Schema(description = "공통 오류 응답")
public class BaseErrorResult {

    @Schema(description = "오류 메시지", example = "error about ...")
    private final String errorMessage;
    @Schema(description = "오류 코드", example = "123")
    private final String errorCode;
}
