package sample.cafekiosk.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ControllerAdvice
 * 여러 컨트롤러 클래스에서 발생할 수 있는 예외를 중앙에서 처리할 수 있는 전역적인 예외 처리 클래스를 정의할 수 있다.
 * 주로 예외 처리, 오류 메시지 변환, 모델 데이터 추가 등과 같은 작업을 수행한다.
 * 이 어노테이션을 사용하는 클래스는 일반적으로 @ExceptionHandler 메소드를 포함하며, 이 메소드들은 해당 컨트롤러 클래스에서 발생하는 예외를 처리한다.
 *
 * @RestControllerAdvice
 * * @ControllerAdvice와 동일한 역할을 수행하지만,
 * 추가적으로 @ResponseBody 어노테이션이 적용되어 있다.
 * 따라서 응답 본문을 생성할 때 데이터를 직렬화하여 JSON이나 XML과 같은 형식으로 반환한다.
 */
@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 실제 Http Status 정의
    @ExceptionHandler(BindException.class) // @NotNull이나 @NotBlank 등과 같은 바인딩 오류시 발생하는 예외
    public ApiResponse<Object> bindException(BindException e) {
        return ApiResponse.of(
                HttpStatus.BAD_REQUEST, // json 메시지 상에서 사용자에게 화면으로 보여주는 상태코드
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                null
        );
    }

}
