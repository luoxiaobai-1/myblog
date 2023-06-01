package my.controller;

import my.blog.annotation.SystemLog;
import my.blog.domain.Dto.Addarticledto;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Dto.ArticleListDto;
import my.blog.domain.Vo.Articlevo;
import my.blog.domain.Vo.Pagevo;
import my.blog.service.ArticleService;
import my.blog.utils.BeanCopy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("content/article")
public class Articleweb {
    @Autowired
    ArticleService articleService;


    @PostMapping
    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    @SystemLog(buinessname = "添加文章")
    public ResponseResult AddArticle(@RequestBody Addarticledto article) {
        return articleService.add(article);

    }
    @PreAuthorize("@ps.hasPermission('content:article:list')")
    @GetMapping("/list")
    public ResponseResult articlelist(ArticleListDto articleListDto) {

        Pagevo pagevo = articleService.Getallarticle(articleListDto);
        return ResponseResult.success(pagevo, HttpCodeEnum.SUCCESS);

    }

    @GetMapping("{id}")
    @SystemLog(buinessname = "查询指定文章")

    public ResponseResult Getarticle(@PathVariable long id) {
        Articlevo articlevo = articleService.getAriticlebyid(id);
        return ResponseResult.success(articlevo, HttpCodeEnum.SUCCESS);

    }

    @PutMapping
    @SystemLog(buinessname = "更改指定文章")
    public ResponseResult updatearticle(@RequestBody Addarticledto article) {
        return articleService.updateArticle(article);

    }

    @DeleteMapping("{id}")
    @SystemLog(buinessname = "删除指定文章")
    public ResponseResult Deletearticle(@PathVariable long id [])
    {
return  articleService.Deletearticle(id);


    }


}
