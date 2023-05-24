package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.Member;
import study.board.exception.EmailDupException;
import study.board.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public String join(Member member) {

        //email 중복 check
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(u -> {
                    throw new EmailDupException(member.getEmail() + "이 이미 존재합니다.");
                });

        //저장
        memberRepository.save(member);


        return "SUCCESS";
    }

    public String login(String email, String password) {
        // 1. Login EMAIL/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
//
//        return tokenInfo;
        return "ok";
    }
}
