package my.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import my.blog.Constants.SystemConstants;
import my.blog.domain.Dto.Addarticledto;
import my.blog.domain.Dto.ArticleListDto;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.*;
import my.blog.domain.entity.Article;
import my.blog.domain.entity.ArticleTag;
import my.blog.domain.entity.Category;
import my.blog.domain.entity.Tag;
import my.blog.exception.SystemException;
import my.blog.mapper.ArticleMapper;
import my.blog.mapper.ArticleTagMapper;
import my.blog.mapper.CategoryDao;
import my.blog.mapper.TagMapper;
import my.blog.service.ArticleService;
import my.blog.service.ArticleTagService;
import my.blog.utils.BeanCopy;
import my.blog.utils.RedisCache;
import my.blog.utils.SecurityUtils;
import net.minidev.json.writer.UpdaterMapper;
import org.apache.ibatis.javassist.expr.NewExpr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author 14574
* @description 针对表【sg_article(文章表)】的数据库操作Service实现
* @createDate 2023-04-22 23:16:17
*/
@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService {
@Autowired
ArticleMapper articleMapper;
@Autowired
    CategoryDao categoryDao;
@Autowired
ArticleTagService articleTagService;
@Autowired
    RedisCache redisCache;
@Autowired
    ArticleTagMapper articleTagMapper;
    @Override
    public List<HotActiclevo> Gethotacticlelist() {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page=  new Page<Article>(1,10);
        page(page,queryWrapper);
        List<HotActiclevo> hotarticles= BeanCopy.copyBeanList(page.getRecords(),HotActiclevo.class);
        return hotarticles;



}

    @Override
    public Pagevo Getacticlelist(Integer pageNum, Integer pageSize, Long categoryId) {
//        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
//        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
//        queryWrapper.orderByDesc(Article::getIsTop);
//
//        Page<Article> page=  new Page<Article>(pageNum,pageSize);
//        List<Article>articles=   page(page,queryWrapper).getRecords();
 //       List<ArticleListvo>  articleListvos=BeanCopy.copyBeanList(articles, ArticleListvo.class);
        Page<ArticleListvo> page = new Page<>(pageNum,pageSize);
        List<ArticleListvo> articleListvos=   articleMapper.getArticleList(page,categoryId).getRecords();

      Pagevo pagevo=new Pagevo(articleListvos,page.getTotal());

    return pagevo;
    }

    @Override
    public Articlevo getAriticle(Integer id) {
        Article article=getById(id);
        if (article==null)
            return null;
        Articlevo articlevo= BeanCopy.copyBean(article, Articlevo.class);
        long categoryid =articlevo.getCategoryId();
        Category category=categoryDao.selectById(categoryid);

        if(category!=null)
        {
            articlevo.setCategoryName(category.getName());
        }
      Integer viewcount = redisCache.getCacheMapValue(SystemConstants.viewcount_Key,id.toString());
        articlevo.setViewCount(viewcount.longValue());
        return articlevo;
    }

    @Override
    public void updateviewcount(Integer id) {

        redisCache.IncreaseCatchmapvValue(SystemConstants.viewcount_Key,id.toString(),1);

    }

    @Override
    public ResponseResult add(Addarticledto article) {
        Article article1=BeanCopy.copyBean(article, Article.class);
        article1.setCreateBy(SecurityUtils.getUserId());
        article1.setCreateTime(new Date());
        save(article1);
article1.setCreateTime(new Date());
article1.setCreateBy(SecurityUtils.getUserId());
        List<ArticleTag> collect = article.getTags().stream().map(tagid -> new ArticleTag(article1.getId(), tagid)).collect(Collectors.toList());

        articleTagService.saveBatch(collect);

        return ResponseResult.success();
    }

    @Override
    public Pagevo Getallarticle(ArticleListDto articleListDto) {
       LambdaQueryWrapper<Article> queryWrapper =new LambdaQueryWrapper();
       queryWrapper.like(StringUtils.hasText(articleListDto.getSummary()) ,Article::getSummary,articleListDto.getSummary());
       queryWrapper.like(StringUtils.hasText(articleListDto.getTitle()), Article::getTitle,articleListDto.getTitle());
       Page<Article> page = new Page<>(articleListDto.getPageNum(),articleListDto.getPageSize());
      page(page,queryWrapper);
        List<ArticleListvo> articleListvos=  BeanCopy.copyBeanList(page.getRecords(),ArticleListvo.class);
        Pagevo pagevo=new Pagevo(articleListvos,page.getTotal());


        return pagevo;



    }

    @Override
    public Articlevo getAriticlebyid(long id) {
        Article article = articleMapper.selectById(id);
        Articlevo articlevo = BeanCopy.copyBean(article, Articlevo.class);
        LambdaQueryWrapper <ArticleTag> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,id);
        List<String> collect = articleTagMapper.selectList(queryWrapper).stream().map(articleTag -> String.valueOf(articleTag.getTagId())).collect(Collectors.toList());
        articlevo.setTags(collect);
        return articlevo;

    }

    @Override
    public ResponseResult updateArticle(Addarticledto article) {
        Article article1=BeanCopy.copyBean(article, Article.class);
        List<Long> tags = article.getTags();
        LambdaUpdateWrapper <ArticleTag> updateWrapper=new LambdaUpdateWrapper<>();
        articleTagMapper.removebyarticle(article1.getId());
        for (Long tag : tags) {
            try {
               ArticleTag articleTag =new ArticleTag(article1.getId(),tag);

             articleTagMapper.insert(articleTag);

            }catch (Exception e)
           {
            throw new SystemException(HttpCodeEnum.SYSTEM_ERROR);
        }

        }
        article1.setUpdateBy(SecurityUtils.getUserId());
        article1.setUpdateTime(new Date());
       LambdaUpdateWrapper<Article> updateWrapper1 =new LambdaUpdateWrapper<>();
        updateWrapper1.eq(Article::getId,article1.getId());
        updateById(article1);

        return ResponseResult.success();
    }

    @Override
    public ResponseResult Deletearticle(long [] id) {
//        for (long l : id) {
//            LambdaUpdateWrapper<Article> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//            lambdaUpdateWrapper.eq(Article::getId,l).
//                    set(Article::getDelFlag, SystemConstants.ARTICLE_STATUS_DRAFT).
//                    set(Article::getUpdateBy,SecurityUtils.getUserId()).
//                    set(Article::getUpdateTime,new Date());
//
//            articleMapper.update(null, lambdaUpdateWrapper);
//        }
articleMapper.deleteByIds(id,new Date(),SecurityUtils.getUserId());

return  ResponseResult.success();
    }


}




