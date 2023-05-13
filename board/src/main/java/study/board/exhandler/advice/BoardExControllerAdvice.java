package study.board.exhandler.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.board.exception.BoardNotFoundException;
import study.board.exhandler.ErrorResult;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice("study.board.controller.api")
public class BoardExControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> boardNotFoundExHandler(BoardNotFoundException e) {
        ErrorResult<Object> errorResult = ErrorResult.builder()
                .errorCode(GlobalErrorCode.NOT_FOUND.getCode())
                .errorMessage(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(errorResult);
    }

    /**
     * @RequestBody bean validation 예외 발생 시
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResult> validationExHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                        .forEach(error->{
                            String fieldName = ((FieldError) error).getField();
                            String errorMessage = error.getDefaultMessage();
                            errors.put(fieldName, errorMessage);
                        });

        ErrorResult<Object> errorResult = ErrorResult.builder()
                .errorCode(GlobalErrorCode.VALIDATION.getCode())
                .errorMessage(GlobalErrorCode.VALIDATION.getMessage())
                .errors(errors)
                .build();
        return ResponseEntity.badRequest().body(errorResult);
    }

    /**
     * @ModelAttribute bean validation 예외 발생 시
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResult> validationExHandler(BindException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(error->{
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        ErrorResult<Object> errorResult = ErrorResult.builder()
                .errorCode(GlobalErrorCode.QUERY_VALIDATION.getCode())
                .errorMessage(GlobalErrorCode.QUERY_VALIDATION.getMessage())
                .errors(errors)
                .build();
        return ResponseEntity.badRequest().body(errorResult);
    }

    @ExceptionHandler
//    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResult> exHandler(Exception e) {
        ErrorResult<Object> errorResult = ErrorResult.builder()
                .errorCode(GlobalErrorCode.INTERNAL_SERVER.getCode())
                .errorMessage(GlobalErrorCode.INTERNAL_SERVER.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(errorResult);
    }
}
