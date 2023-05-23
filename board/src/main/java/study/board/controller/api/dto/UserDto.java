package study.board.controller.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import study.board.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class UserDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserJoinRequest {

        @Email
        private String email;

        @Length(min = 8, max = 15)
        //regular expression, ^:문자열 시작, \S:공백을 제외한 모든 문자, $:문자열의 끝
        @Pattern(regexp = "^\\S+$", message = "공백을 포함할 수 없습니다.")
        private String password;

        public User toEntity() {
            return User.createUser(email, password);
        }
    }
}
