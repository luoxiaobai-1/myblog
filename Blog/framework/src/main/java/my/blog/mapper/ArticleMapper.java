package my.blog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import my.blog.domain.Vo.ArticleListvo;
import my.blog.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.ManagedBean;
import java.util.Date;
import java.util.List;

/**
* @author 14574
* @description 针对表【sg_article(文章表)】的数据库操作Mapper
* @createDate 2023-04-22 23:20:14
* @Entity a.entity.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
  IPage< ArticleListvo> getArticleList(IPage< ArticleListvo> page,@Param("categoryId") Long categoryId);
  boolean deleteByIds(@Param("id") long [] id, @Param("time") Date time, @Param("personid") long personid);

}




