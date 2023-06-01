package my.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.blog.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
* @author 14574
* @description 针对表【sg_tag(标签)】的数据库操作Mapper
* @createDate 2023-05-01 20:55:02
* @Entity .entity.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    boolean deleteByIds(@Param("id") long [] id, @Param("time") Date time, @Param("personid") long personid);

}




