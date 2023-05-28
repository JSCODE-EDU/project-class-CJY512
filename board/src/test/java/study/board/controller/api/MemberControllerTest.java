package study.board.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import study.board.exception.EmailDupException;
import study.board.service.MemberService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static study.board.controller.api.dto.MemberDto.*;

@WebMvcTest(controllers = MemberApiController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공")
    public void join() throws Exception {
        String email = "cjy@cjy.com";
        String password = "1q2w3e4r";

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(MemberJoinRequest.builder()
                                .email(email)
                                .password(password)
                                .build()
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("회원가입 실패 - email 중복")
    public void join_fail() throws Exception {
        String email = "cjy@cjy.com";
        String password = "1q2w3e4r";

        when(memberService.join(any()))
                .thenThrow(new EmailDupException("사용중인 이메일주소입니다."));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(MemberJoinRequest.builder()
                                .email(email)
                                .password(password)
                                .build()
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isConflict());

    }

}