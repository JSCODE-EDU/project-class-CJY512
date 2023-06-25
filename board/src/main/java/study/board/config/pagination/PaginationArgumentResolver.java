package study.board.config.pagination;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PaginationArgumentResolver extends PageableHandlerMethodArgumentResolver {

    @Override
    public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
        if (methodParameter.hasMethodAnnotation(PaginationLimit.class)) {
            final int maxSize = methodParameter.getMethodAnnotation(PaginationLimit.class).maxSize();
            if (pageable.getPageSize() > maxSize) {
                throw new IllegalArgumentException("Page size must be under " + maxSize);
            }
        }
        return pageable;
    }
}
