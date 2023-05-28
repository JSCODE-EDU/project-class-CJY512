package study.board.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.board.config.jwt.JwtUtil;
import study.board.config.jwt.TokenInfo;
import study.board.controller.api.dto.GlobalResponseCode;
import study.board.controller.api.dto.Result;
import study.board.entity.Member;
import study.board.service.MemberService;

import javax.servlet.http.HttpServletRequest;

import static study.board.controller.api.dto.MemberDto.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public ResponseEntity join(@Validated @RequestBody MemberJoinRequest memberJoinRequest) {
        memberJoinRequest.encodePassword(passwordEncoder.encode(memberJoinRequest.getPassword()));
        String result = memberService.join(memberJoinRequest.toEntity());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@Validated @RequestBody MemberLoginRequest memberLoginRequest) {
        String email = memberLoginRequest.getEmail();
        String password = memberLoginRequest.getPassword();
        TokenInfo tokenInfo = memberService.login(email, password);
        String authorization = JwtUtil.TOKEN_PREFIX + tokenInfo.getAccessToken();
        return ResponseEntity.ok().header(JwtUtil.HEADER, authorization).body(tokenInfo);
    }

    @GetMapping("/mypage")
    public Result<MemberResponse> myPage(HttpServletRequest request) {
        MemberResponse memberResponse = MemberResponse.fromEntity(memberService.myPage(request));
        return Result.<MemberResponse>builder()
                .code(GlobalResponseCode.SUCCESS_MEMBER.getCode())
                .message(GlobalResponseCode.SUCCESS_MEMBER.getMessage())
                .data(memberResponse)
                .build();
    }
}
