package my.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import my.blog.domain.Dto.UserDto;
import my.blog.domain.Dto.UserListDto;
import my.blog.domain.Dto.UserstatusDto;
import my.blog.domain.Dto.UserupdateDto;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.SeletedUservo;
import my.blog.domain.entity.User;

/**
* @author 14574
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-04-26 20:56:19
*/
public interface UserService extends IService<User> {

    ResponseResult Getuserinfo();

    ResponseResult Updateuserinfo(User user);

    ResponseResult insertuser(User user);

    Pagevo getUsers(UserListDto listDto);

    boolean AddUserBYAdmin(UserDto userDto);

    boolean Deleteuser(long[] id);

    SeletedUservo getUserbyid(long id);

    void UpdateUserBYAdmin(UserupdateDto userDto);

    boolean ChangeStaus(UserstatusDto userstatusDto);
}
