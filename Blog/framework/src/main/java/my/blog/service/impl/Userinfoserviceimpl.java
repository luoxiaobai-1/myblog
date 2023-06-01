package my.blog.service.impl;

import my.blog.domain.Vo.Adminuserinfovo;
import my.blog.domain.Vo.UserInfoVo;
import my.blog.domain.entity.User;
import my.blog.mapper.MenuMapper;
import my.blog.mapper.RoleMapper;
import my.blog.service.MenuService;
import my.blog.service.RoleService;
import my.blog.service.Userinfoservice;
import my.blog.utils.BeanCopy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Userinfoserviceimpl implements Userinfoservice {
   @Autowired
   MenuService menuService;
   @Autowired
    RoleService roleService;


    @Override
    public Adminuserinfovo GetuserInfo(User user) {
long id= user.getId();

        UserInfoVo userInfoVo= BeanCopy.copyBean(user,UserInfoVo.class);

        return new Adminuserinfovo(menuService.getperms(id),roleService.Getrolekey(id),userInfoVo);
    }
}
