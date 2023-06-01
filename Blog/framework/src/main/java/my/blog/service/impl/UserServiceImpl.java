package my.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import my.blog.domain.Dto.UserDto;
import my.blog.domain.Dto.UserListDto;
import my.blog.domain.Dto.UserstatusDto;
import my.blog.domain.Dto.UserupdateDto;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.*;
import my.blog.domain.entity.Tag;
import my.blog.domain.entity.User;
import my.blog.domain.entity.UserRole;
import my.blog.exception.SystemException;
import my.blog.mapper.RoleMapper;
import my.blog.mapper.UserMapper;
import my.blog.mapper.UserRoleMapper;
import my.blog.service.RoleService;
import my.blog.service.UserRoleService;
import my.blog.service.UserService;
import my.blog.utils.BeanCopy;
import my.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 14574
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2023-04-26 20:56:19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
@Autowired
  private   PasswordEncoder passwordEncoder;
@Autowired
private UserRoleMapper userRoleMapper;
@Autowired
    UserRoleService userRoleService;
@Autowired
RoleService roleService;
    @Override
    public ResponseResult Getuserinfo() {

        Long userid = SecurityUtils.getUserId();
        User user = getById(userid);
        UserInfoVo userInfoVo = BeanCopy.copyBean(user, UserInfoVo.class);
        return ResponseResult.success(userInfoVo, HttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult Updateuserinfo(User user) {
       updateById(user);
       return ResponseResult.success();
    }

    @Override
    public ResponseResult insertuser(User user) {
        if(!StringUtils.hasText(user.getUserName()))
        {
            throw new SystemException(HttpCodeEnum.REQUIRE_USERNAME);
        }
        if(!StringUtils.hasText(user.getPassword()))
        {
            throw new SystemException(HttpCodeEnum.LOGIN_ERROR);
        }
        if(!StringUtils.hasText(user.getEmail()))
        {
            throw new SystemException(HttpCodeEnum.ILLEGAL_PARAMETER);
        }
        if(!StringUtils.hasText(user.getNickName()))
        {
            throw new SystemException(HttpCodeEnum.ILLEGAL_PARAMETER);
        }
if (userNameExist(user.getUserName()))
{
    throw new SystemException(HttpCodeEnum.USERNAME_EXIST);
}

if (NickNameExist(user.getNickName()))
        {
            throw new SystemException(HttpCodeEnum.NickeName_EXIST);
        }

String password=passwordEncoder.encode(user.getPassword());
user.setPassword(password);
user.setCreateBy(-1L);
user.setCreateTime(new Date());
save(user);
userRoleMapper.insert(new UserRole(user.getId(),14L));
return ResponseResult.success();



    }

    @Override
    public Pagevo getUsers(UserListDto listDto) {
        LambdaQueryWrapper <User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(listDto.getPhonenumber()),User::getPhonenumber,listDto.getPhonenumber());
        queryWrapper.eq(StringUtils.hasText(listDto.getStatus()),User::getStatus,listDto.getStatus());
        queryWrapper.like(StringUtils.hasText(listDto.getUserName()),User::getUserName,listDto.getUserName());
        Page<User> page = new Page<>();
        page.setCurrent(listDto.getPageNum());
        page.setSize(listDto.getPageSize());
        page(page, queryWrapper);
        List<Uservo> uservos = BeanCopy.copyBeanList(page.getRecords(), Uservo.class);
        return new Pagevo(uservos, page.getTotal());

    }
@Transactional
    @Override
    public boolean AddUserBYAdmin(UserDto userDto) {
        User user = BeanCopy.copyBean(userDto, User.class);
if (NickNameExist(user.getNickName())&&PhoneExist(user.getPhonenumber())&&userNameExist(user.getUserName()))
{
    throw new SystemException(HttpCodeEnum.EMAIL_EXIST.getCode(),"邮箱或电话号码或用户名重复！！！");
}
        String password=passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setCreateBy(SecurityUtils.getUserId());
        user.setCreateTime(new Date());
        save(user);
userDto.getRoleIds().forEach(roleid->userRoleMapper.insert(new UserRole(user.getId(),roleid)));

        return true;
    }
@Transactional
    @Override
    public boolean Deleteuser(long[] id) {
        if (Arrays.stream(id).anyMatch(f->f==1L))
        {
            throw  new SystemException(HttpCodeEnum.CAN_NOT_DELETE_ADMIN);
        }
   return      getBaseMapper().deleteByIds(id,new Date(),SecurityUtils.getUserId());
    }

    @Override
    public SeletedUservo getUserbyid(long id) {
        LambdaQueryWrapper<UserRole> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        List<UserRole> roles =userRoleService.list(queryWrapper);
        List<Rolevo> rolevos;
        List<Long> roleids;
try {
     roleids = roles.stream().
            map(UserRole::getRoleId).
            collect(Collectors.toList());
  rolevos = BeanCopy.copyBeanList(roleService.listByIds(roleids), Rolevo.class);
}
catch (Exception e)
{
    throw  new SystemException(HttpCodeEnum.ROLE_NOT_EXIT);
}

        User user = getById(id);
        System.out.println(user.toString());
        Uservo uservo = BeanCopy.copyBean(user, Uservo.class);
        SeletedUservo seletedUservo=new SeletedUservo(roleids,rolevos,uservo);
        return seletedUservo;
    }
@Transactional
    @Override
    public void UpdateUserBYAdmin(UserupdateDto userDto) {
        User user = BeanCopy.copyBean(userDto, User.class);
        user.setUpdateBy(SecurityUtils.getUserId());
        user.setUpdateTime(new Date());
        LambdaQueryWrapper<UserRole>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,userDto.getId());
        userRoleService.remove(queryWrapper);
        updateById(user);
        userDto.getRoleIds().forEach(roleid->userRoleMapper.insert(new UserRole(user.getId(),roleid)));

    }

    @Override
    public boolean ChangeStaus(UserstatusDto userstatusDto) {
        LambdaUpdateWrapper<User> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId,userstatusDto.getUserId()).set(User::getStatus,userstatusDto.getStatus()).set(User::getUpdateBy,SecurityUtils.getUserId())
                .set(User::getUpdateTime,new Date());
        return update(updateWrapper);
    }

    private boolean NickNameExist(String nickName) {
        LambdaQueryWrapper <User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return    count(queryWrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper <User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
     return    count(queryWrapper)>0;

    }
    private boolean PhoneExist(String phone) {
        LambdaQueryWrapper <User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phone);
        return    count(queryWrapper)>0;

    }

}




