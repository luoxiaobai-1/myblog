package my.controller;


import my.blog.domain.Dto.UserDto;
import my.blog.domain.Dto.UserListDto;
import my.blog.domain.Dto.UserstatusDto;
import my.blog.domain.Dto.UserupdateDto;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.SeletedUservo;
import my.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
public class Userweb {
    @Autowired
    UserService userService;
    @GetMapping("/list")
    public ResponseResult userList(UserListDto listDto)
    {

        Pagevo pagevo= userService.getUsers(listDto);
return ResponseResult.success(pagevo, HttpCodeEnum.SUCCESS);
    }
    @PostMapping
    public  ResponseResult Adduser(@RequestBody @Validated  UserDto userDto)
    {
        userService.AddUserBYAdmin(userDto);
return  ResponseResult.success();

    }
    @DeleteMapping ("/{id}")
    public  ResponseResult DeleteUser(@PathVariable long[] id)
    {

      boolean flag=  userService.Deleteuser(id);
        return flag?ResponseResult.success():ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
    }
    @GetMapping("/{id}")
    public  ResponseResult Selecteduser(@PathVariable long id)
    {
        SeletedUservo seletedUservo=userService.getUserbyid(id);
return ResponseResult.success(seletedUservo,HttpCodeEnum.SUCCESS);
    }

    @PutMapping
    public ResponseResult UpdateUser(@RequestBody @Validated UserupdateDto userDto)
    {
        userService.UpdateUserBYAdmin(userDto);
return ResponseResult.success();

    }
@PutMapping("/changeStatus")
    public  ResponseResult ChangSruasus(@RequestBody @Validated UserstatusDto userstatusDto)
{

    boolean status =userService.ChangeStaus(userstatusDto);
    return status ? ResponseResult.success() : ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
}

}
