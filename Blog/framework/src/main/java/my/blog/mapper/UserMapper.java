package my.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.blog.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
* @author 14574
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-04-26 20:56:19
* @Entity .entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    boolean deleteByIds(@Param("id") long [] id, @Param("time") Date time, @Param("personid") long personid);
}




