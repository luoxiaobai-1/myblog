package my.controller;

import lombok.extern.slf4j.Slf4j;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.entity.User;
import my.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class Userweb {
    @Autowired
    private UserService userService;

@GetMapping("/userInfo")

    public ResponseResult userinfo()
    {

        return userService.Getuserinfo();
    }

    @PutMapping("/userInfo")
    public ResponseResult updateuserinfo(@RequestBody User user)
    {

        return userService.Updateuserinfo(user);
    }

    @PostMapping("/register")
    public  ResponseResult register(@RequestBody User user)
    {

        return  userService.insertuser(user);
    }

}
