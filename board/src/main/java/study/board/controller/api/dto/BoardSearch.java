package study.board.controller.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Schema(description = "게시글 검색 DTO")
@Data
public class BoardSearch {

    @NotBlank
    @Schema(description = "검색 키워드", example = "title1", required = true)
    private String query;
}
