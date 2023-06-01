package my.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import my.blog.Constants.SystemConstants;
import my.blog.domain.entity.LoginUser;
import my.blog.domain.entity.User;
import my.blog.mapper.MenuMapper;
import my.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Slf4j
@Service
public class Userdetailservceiml implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<my.blog.domain.entity.User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(my.blog.domain.entity.User::getUserName,username);
        queryWrapper.eq(my.blog.domain.entity.User::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
       User user=userMapper.selectOne(queryWrapper);

       if (Objects.isNull(user)){

           throw  new RuntimeException("用户不存在");

       }
       //ToDo 查询权限信息封装
if (SystemConstants.ADMIN.equals(user.getType()))
{
    List<String> strings = menuMapper.selectPermsbyuserid(user.getId());


return new LoginUser(user,strings);

}


        return new LoginUser(user,menuMapper.selectPermsbyuserid(user.getId()));
    }
}
