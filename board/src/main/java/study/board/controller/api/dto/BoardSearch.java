package study.board.controller.api.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class BoardSearch {

    @NotBlank
    private String query;
}
