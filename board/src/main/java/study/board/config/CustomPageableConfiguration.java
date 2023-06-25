package study.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class CustomPageableConfiguration {
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return (resolver) -> {
//            resolver.setPageParameterName(pageable.getPageParameter());
//            resolver.setSizeParameterName(pageable.getSizeParameter());
//            resolver.setOneIndexedParameters(pageable.isOneIndexedParameters());
//            resolver.setPrefix(pageable.getPrefix());
//            resolver.setQualifierDelimiter(pageable.getQualifierDelimiter());
            resolver.setFallbackPageable(PageRequest.of(0, 100));
            resolver.setMaxPageSize(100);
        };
    }
}