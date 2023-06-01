package my.controller;

import io.swagger.annotations.ApiOperation;
import my.blog.annotation.SystemLog;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.Adminuserinfovo;
import my.blog.domain.Vo.UserInfoVo;
import my.blog.domain.Vo.routersvo;
import my.blog.domain.entity.LoginUser;
import my.blog.domain.entity.User;
import my.blog.exception.SystemException;
import my.blog.service.Loginservice;
import my.blog.service.MenuService;
import my.blog.service.RoleService;
import my.blog.service.Userinfoservice;
import my.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.awt.geom.RectangularShape;

@RestController
public class Loginweb {
@Autowired
Loginservice loginservice;

@Autowired
private Userinfoservice userinfoservice;
@Autowired MenuService menuService;

    @PostMapping("/user/login")
    public ResponseResult Login(@RequestBody User user)
    {
if (!StringUtils.hasText(user.getUserName()))
{
throw  new SystemException(HttpCodeEnum.REQUIRE_USERNAME);

}
 return loginservice.login(user);
    }

    @GetMapping("/getInfo")
    @SystemLog(buinessname = "系统登录接口")
    public ResponseResult <Adminuserinfovo> getinfo()
    {

        LoginUser loginUser = SecurityUtils.getLoginUser();

if (loginUser==null)
{
    throw new SystemException(HttpCodeEnum.ROLE_NOT_EXIT);
}
      Adminuserinfovo adminuserinfovo=userinfoservice.GetuserInfo(loginUser.getUser());

return  ResponseResult.success(adminuserinfovo,HttpCodeEnum.SUCCESS);

    }

    @GetMapping("/getRouters")
    public ResponseResult <routersvo> getrouters()
    { LoginUser loginUser = SecurityUtils.getLoginUser();

routersvo routersvo=menuService.GetrouterTree(loginUser.getUser().getId());

        return  ResponseResult.success(routersvo,HttpCodeEnum.SUCCESS);

    }
    @PostMapping("/user/logout")
    public ResponseResult  logout()
    { LoginUser loginUser = SecurityUtils.getLoginUser();

        loginservice.logout(loginUser.getUser().getId());

        return  ResponseResult.success();

    }

}
