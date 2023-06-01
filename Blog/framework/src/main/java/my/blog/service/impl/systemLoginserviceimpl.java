package my.blog.service.impl;

import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.Loginvo;
import my.blog.domain.Vo.UserInfoVo;
import my.blog.domain.entity.LoginUser;
import my.blog.domain.entity.User;
import my.blog.service.Loginservice;
import my.blog.utils.BeanCopy;
import my.blog.utils.JwtUtil;
import my.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class systemLoginserviceimpl implements Loginservice {
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


        redisCache.setCacheObject("login"+userid,loginUser);
        Map <String ,String> map=new HashMap<>();
        map.put("token",token);
        return ResponseResult.success(map, HttpCodeEnum.SUCCESS) ;

    }

    @Override
    public ResponseResult logout(long id) {
        redisCache.deleteObject("login"+id);
        return ResponseResult.success();
    }

}

