package study.board.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.board.service.UserService;

import static study.board.controller.api.dto.UserDto.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity join(@Validated @RequestBody UserJoinRequest userJoinRequest) {
        String result = userService.join(userJoinRequest.toEntity());
        return ResponseEntity.ok().body("회원가입 성공");
    }
}
