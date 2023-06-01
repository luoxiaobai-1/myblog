package my.blog.exception;


import lombok.Getter;
import lombok.ToString;
import my.blog.domain.Result.HttpCodeEnum;

@Getter
@ToString
public class SystemException extends RuntimeException{
    private final Integer code;
    private final String message;

    public SystemException(HttpCodeEnum httpCodeEnum){
        super(httpCodeEnum.getMessage());
        this.code=httpCodeEnum.getCode();
        this.message= httpCodeEnum.getMessage();
    }
    public SystemException(Integer httpCode,String message){
        super(message);
        this.code=httpCode;
        this.message= message;
    }
}
