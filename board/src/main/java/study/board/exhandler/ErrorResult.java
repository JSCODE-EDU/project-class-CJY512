package study.board.exhandler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
@Schema(description = "검증 오류 응답")
public class ErrorResult extends BaseErrorResult {

    @Schema(description = "필드와 검증 내용", example = "error")
    private Map<String, String> details;
}
