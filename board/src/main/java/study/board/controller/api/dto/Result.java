package study.board.controller.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
@Builder
@Schema(description = "Global response")
public class Result<T> {

    @Schema(description = "response code", example = "000")
    private final String code;
    @Schema(description = "response message", example = "success")
    private final String message;
    private final T data;
}
