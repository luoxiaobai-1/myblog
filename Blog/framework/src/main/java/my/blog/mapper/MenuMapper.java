package my.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.blog.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 14574
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-05-01 22:06:21
* @Entity .entity.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List <String> selectPermsbyuserid(@Param("userid") long userid);
    List<Menu> selectallrouters();
    List<Menu> selectallroutersbyid(long id);
    List <Long> selectMenuListByRoleId(long roleId);

}




