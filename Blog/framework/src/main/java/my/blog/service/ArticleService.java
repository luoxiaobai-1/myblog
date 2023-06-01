package my.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import my.blog.domain.Dto.Addarticledto;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Dto.ArticleListDto;
import my.blog.domain.Vo.Articlevo;
import my.blog.domain.Vo.HotActiclevo;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.entity.Article;

import java.util.List;

/**
* @author 14574
* @description 针对表【sg_article(文章表)】的数据库操作Service
* @createDate 2023-04-22 23:16:17
*/
public interface ArticleService extends IService<Article> {

    List<HotActiclevo> Gethotacticlelist();

   Pagevo Getacticlelist(Integer pageNum, Integer pageSize, Long categoryId);
   Articlevo getAriticle(Integer id);

    void updateviewcount(Integer id);



    ResponseResult add(Addarticledto article);

    Pagevo Getallarticle(ArticleListDto articleListDto);

    Articlevo getAriticlebyid(long id);

    ResponseResult updateArticle(Addarticledto article);

    ResponseResult Deletearticle(long [] id);

}
