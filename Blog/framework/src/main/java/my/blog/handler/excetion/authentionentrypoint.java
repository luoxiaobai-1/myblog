package my.blog.handler.excetion;

import com.alibaba.fastjson.JSON;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class authentionentrypoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
         authException.printStackTrace();
        ResponseResult<Object> responseResult = null;
         if (authException instanceof BadCredentialsException)
         {
             responseResult=  ResponseResult.errorResult(HttpCodeEnum.LOGIN_ERROR);

         } else if (authException instanceof InsufficientAuthenticationException){
             responseResult=  ResponseResult.errorResult(HttpCodeEnum.NEED_LOGIN);
         }
         else
         {

             responseResult=  ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
         }
        WebUtils.renderString(response, JSON.toJSONString( responseResult));
    }
}
