package my.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.blog.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 14574
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
* @createDate 2023-05-01 22:50:28
* @Entity .entity.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    boolean removeRolebyuserid(@Param("id") long [] id);
}




