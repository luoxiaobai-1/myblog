package my.blog.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import my.blog.domain.entity.UserRole;
import my.blog.mapper.UserRoleMapper;
import my.blog.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
* @author 14574
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2023-05-01 22:50:28
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {

}




