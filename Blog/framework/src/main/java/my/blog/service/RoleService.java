package my.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import my.blog.domain.Dto.RoleAddDto;
import my.blog.domain.Dto.RoleDto;
import my.blog.domain.Dto.RoleListDto;
import my.blog.domain.Dto.RolemessDto;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.Rolevo;
import my.blog.domain.entity.Role;

import java.util.List;

/**
* @author 14574
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2023-05-01 22:15:04
*/
public interface RoleService extends IService<Role> {

    public List<String> Getrolekey(long id);

    Pagevo GetRoleList(RoleListDto roleListDto);

    boolean setstuatus(String roleId, String status);

   boolean AddRole(RoleDto roleDto);

    RolemessDto GetRole(long id);

    boolean AlterRole(RoleAddDto roleDto);

   void DeleteRole(long [] id);

    List<Rolevo> roles();
}
