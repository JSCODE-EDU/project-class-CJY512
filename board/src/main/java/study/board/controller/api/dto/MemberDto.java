package study.board.controller.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import study.board.entity.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class MemberJoinRequest {

        @Email
        @NotBlank
        private String email;

        @NotBlank
        @Length(min = 8, max = 15)
        //regular expression, ^:문자열 시작, \S:공백을 제외한 모든 문자, $:문자열의 끝
        @Pattern(regexp = "^\\S+$", message = "공백을 포함할 수 없습니다.")
        private String password;

        public Member toEntity() {
            return Member.createUser(email, password);
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class MemberLoginRequest {

        @Email
        @NotBlank
        private String email;

        @NotBlank
        @Length(min = 8, max = 15)
        //regular expression, ^:문자열 시작, \S:공백을 제외한 모든 문자, $:문자열의 끝
        @Pattern(regexp = "^\\S+$", message = "공백을 포함할 수 없습니다.")
        private String password;
    }
}
