package my.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.entity.LoginUser;
import my.blog.exception.SystemException;
import my.blog.utils.JwtUtil;
import my.blog.utils.RedisCache;
import my.blog.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.EOFException;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class jwtauthenticationfliter extends OncePerRequestFilter {
    @Autowired
    RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");


        if (!StringUtils.hasText(token))
        {
            filterChain.doFilter(request,response);
        return;
        }
        Claims claims=null;
        try {
            claims= JwtUtil.parseJWT(token);
        } catch (Exception e)
        {
            WebUtils.renderString(response, JSON.toJSONString(HttpCodeEnum.NEED_LOGIN));
            return;
            
        }

        String userid=claims.getSubject();
        LoginUser loginUser = redisCache.getCacheObject("login" + userid);


        if (Objects.isNull(loginUser))
        {
            ResponseResult<Object> result = ResponseResult.errorResult(HttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;


        }

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);


    }
}
