package my.blog.service.impl;

import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.Loginvo;
import my.blog.domain.Vo.UserInfoVo;
import my.blog.domain.entity.LoginUser;
import my.blog.domain.entity.User;
import my.blog.service.BlogLoginservice;
import my.blog.utils.BeanCopy;
import my.blog.utils.JwtUtil;
import my.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class BlogLoginserviceiml implements BlogLoginservice {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
       Authentication authentication=authenticationManager.authenticate(authenticationToken);
       if (Objects.isNull(authentication))
       {
           throw new RuntimeException("用户名或密码错误");

       }
        LoginUser loginUser=(LoginUser) authentication.getPrincipal();
       String userid=loginUser.getUser().getId().toString();
    String token=    JwtUtil.createJWT(userid);
     UserInfoVo userInfoVo=  BeanCopy.copyBean(loginUser.getUser(), UserInfoVo.class);
        Loginvo loginvo=new Loginvo(token,userInfoVo);
        redisCache.setCacheObject("bloglogin"+userid,loginUser);
        return ResponseResult.success(loginvo, HttpCodeEnum.SUCCESS) ;
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser= (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getUser().getId();
        redisCache.deleteObject("bloglogin"+id);
        return ResponseResult.success();

    }


}
