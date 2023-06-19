package study.board.controller.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LikeDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeResponse {
        //true:=좋아요
        //false:=좋아요 취소
        private boolean isLike;
    }

}
