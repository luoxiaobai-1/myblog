package my.controller;

import lombok.extern.slf4j.Slf4j;
import my.blog.annotation.SystemLog;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;

import my.blog.domain.Vo.Articlevo;
import my.blog.domain.Vo.HotActiclevo;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.entity.Category;
import my.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article")
public class Articleweb {
    @Autowired
    ArticleService articleService;

    @GetMapping("/hotArticleList")
    @SystemLog(buinessname = "热门信息")
    public ResponseResult<List<HotActiclevo>> Hotacticlelist() {

        List<HotActiclevo> acticlevos = articleService.Gethotacticlelist();
        return ResponseResult.success(acticlevos, HttpCodeEnum.SUCCESS);
    }
    @SystemLog(buinessname = "文章列表")
    @GetMapping("/articleList")
    public ResponseResult<Pagevo> ArticleList(Integer pageNum, Integer pageSize, Long categoryId) {

        Pagevo pagevo = articleService.Getacticlelist(pageNum, pageSize, categoryId);
        return ResponseResult.success(pagevo, HttpCodeEnum.SUCCESS);

    }

    @GetMapping("/{id}")
    public ResponseResult<Articlevo> ArticleList(@PathVariable("id") Integer id) {

        Articlevo articlevo = articleService.getAriticle(id);
        return articlevo != null ? ResponseResult.success(articlevo, HttpCodeEnum.SUCCESS) : ResponseResult.errorResult(HttpCodeEnum.NO_OPERATOR_AUTH);

    }
    @PutMapping ("/updateViewCount/{id}")
    public ResponseResult updateviewcount(@PathVariable("id") Integer id) {

       articleService.updateviewcount(id);

       return ResponseResult.success();
    }
}
