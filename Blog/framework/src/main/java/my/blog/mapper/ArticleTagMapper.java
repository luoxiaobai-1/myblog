package my.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.blog.domain.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
* @author 14574
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Mapper
* @createDate 2023-05-06 13:25:49
* @Entity .entity.ArticleTag
*/
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
 boolean     updatetagbyid(@Param("article") long articleid,@Param("tag") long Tagid);
 boolean removebyarticle(@Param("article") long articleid);

}




