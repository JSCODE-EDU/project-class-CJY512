package study.board.config.pagination;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface PaginationLimit {
    int maxSize() default 2000;
}
