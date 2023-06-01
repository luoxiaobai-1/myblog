package my.blog.handler;

import lombok.extern.slf4j.Slf4j;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.exception.SystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class Glaobalexcetionhandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemexceptionhandler(SystemException systemException)
    {
       log.error(systemException.getMessage());

       return ResponseResult.errorResult(systemException.getCode(),systemException.getMessage());


    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult NotValidexceptionhandler(MethodArgumentNotValidException Exception)
    {
        log.error(Exception.getMessage());

        return ResponseResult.errorResult(HttpCodeEnum.ILLEGAL_PARAMETER.getCode(), Exception.getMessage());


    }
}
