package my.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.blog.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author 14574
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-05-01 22:15:04
* @Entity .entity.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List <String> selectRoleKeyById(long id);
    boolean deleteByIds(@Param("id") long [] id, @Param("time") Date time, @Param("personid") long personid);

}




