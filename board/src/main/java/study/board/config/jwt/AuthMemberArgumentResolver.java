package study.board.config.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import study.board.entity.Member;
import study.board.exception.MemberNotFoundException;
import study.board.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    //컨트롤러 함수가 실행하기 전에 수행됨
    //컨트롤러에 정의된 어노테이션과 파라미터 타입을 확인
    // ->밑의 resolveArgument를 내가 정한 어노테이션과 파라미터 타입인지 검증하는거임.
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(JwtAuth.class);
        boolean isMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasAnnotation && isMemberType;
    }

    //위 함수 true로 통과하면 파라미터에 어떤 값을 넣어줄 지 리턴
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return memberRepository.findById(jwtUtil.getMemberId(request))
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));
    }
}
