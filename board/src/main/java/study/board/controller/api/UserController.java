package study.board.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.board.service.MemberService;

import static study.board.controller.api.dto.MemberDto.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity join(@Validated @RequestBody MemberJoinRequest memberJoinRequest) {
        String result = memberService.join(memberJoinRequest.toEntity());
        return ResponseEntity.ok().body("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody MemberLoginRequest memberLoginRequest) {
        String email = memberLoginRequest.getEmail();
        String password = memberLoginRequest.getPassword();
        String result = memberService.login(email, password);
        return ResponseEntity.ok(result);
    }
}
