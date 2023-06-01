package my.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.blog.domain.Vo.Categoryvo;
import my.blog.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


/**
 * 分类表(SgCategory)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-23 22:33:41
 */
@Mapper
public interface CategoryDao extends BaseMapper<Category> {
    List<Categoryvo> getcategory();

    boolean deleteByIds(@Param("id") long [] id, @Param("time") Date time, @Param("personid") long personid);
}

