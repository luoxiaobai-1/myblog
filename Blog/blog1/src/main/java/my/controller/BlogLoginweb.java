package my.controller;

import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.entity.User;
import my.blog.exception.SystemException;
import my.blog.service.BlogLoginservice;
import my.blog.service.impl.BlogLoginserviceiml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginweb {
@Autowired
    BlogLoginservice loginservice;

    @PostMapping("/login")
    public ResponseResult Login(@RequestBody User user)
    {

if (!StringUtils.hasText(user.getUserName()))
{
throw  new SystemException(HttpCodeEnum.REQUIRE_USERNAME);

}
return loginservice.login(user);
    }
    @PostMapping("/logout")
    public ResponseResult logout()
    {
        return loginservice.logout();

    }
}
