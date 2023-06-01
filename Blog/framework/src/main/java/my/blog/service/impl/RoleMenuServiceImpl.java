package my.blog.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import my.blog.domain.entity.RoleMenu;
import my.blog.mapper.RoleMenuMapper;
import my.blog.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
* @author 14574
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2023-05-09 21:51:56
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService {

}




