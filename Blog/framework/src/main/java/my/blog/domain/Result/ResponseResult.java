package my.blog.domain.Result;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public ResponseResult() {
        this.setCode(HttpCodeEnum.SUCCESS.getCode());
        this.setMessage(HttpCodeEnum.SUCCESS.getMessage());
    }

    public ResponseResult(HttpCodeEnum httpCodeEnum, T data) {
        this.code = httpCodeEnum.getCode();
        this.message = httpCodeEnum.getMessage();
        this.data = data;
    }

    public static <T> ResponseResult<T> success(T data,HttpCodeEnum httpCodeEnum) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setData(data);
        responseResult.setMessage(httpCodeEnum.getMessage());
        responseResult.setCode(httpCodeEnum.getCode());
        return responseResult;
    }
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>();
    }

    public static <T> ResponseResult<T> errorResult(HttpCodeEnum httpCodeEnum) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setMessage(httpCodeEnum.getMessage());
        responseResult.setCode(httpCodeEnum.getCode());
        return responseResult;
    }

    public static <T> ResponseResult<T> errorResult(Integer code, String message) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setMessage(message);
        responseResult.setCode(code);
        return responseResult;
    }
}
