package my.blog.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.blog.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
* @author 14574
* @description 针对表【sg_link(友链)】的数据库操作Mapper
* @createDate 2023-04-26 20:24:13
* @Entity .entity.Link
*/
@Mapper
public interface LinkMapper extends BaseMapper<Link> {
    boolean deleteByIds(@Param("id") long [] id, @Param("time") Date time, @Param("personid") long personid);
}




