package my.controller;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.Linkvo;
import my.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/link")
public class Linkweb {

    @Autowired
    LinkService linkService;
    @GetMapping("/getAllLink")
    public ResponseResult<List<Linkvo>> GetAllLink()
    {
        List<Linkvo> linkvoList=       linkService.GetallLink();

        return ResponseResult.success(linkvoList, HttpCodeEnum.SUCCESS);

    }
}
