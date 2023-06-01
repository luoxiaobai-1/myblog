package my.controller;

import my.blog.domain.Dto.*;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.Linkvo;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.Tagvo;
import my.blog.domain.entity.Link;
import my.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class Linkweb {

    @Autowired
    LinkService linkService;
    @GetMapping("/list")
    public ResponseResult linkList(LinkListDto linkListDto)
    {

        Pagevo pagevo =linkService.getLinks(linkListDto);
        return  ResponseResult.success(pagevo, HttpCodeEnum.SUCCESS);
    }
    @PostMapping
    public ResponseResult AddLink(@RequestBody @Validated LinkDto linkDto)
    {
        boolean flag= linkService.Addlink(linkDto);

        return flag?ResponseResult.success():ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);


    }
    @GetMapping("/{id}")
    public ResponseResult GetLink(@PathVariable long id)
    {
       Linkvo linkvo= linkService.GetLink(id);

        return ResponseResult.success(linkvo,HttpCodeEnum.SUCCESS);

    }
    @PutMapping()
    public ResponseResult updateLink( @RequestBody Linkvo linkvo )
    {
      boolean flag=  linkService.updateLink(linkvo);

        return flag?ResponseResult.success():ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);

    }
    @PutMapping("/changeLinkStatus")
    public ResponseResult ChangeStatus(@RequestBody @Validated LinkStatusDto statusDto)
    {
      boolean flag=  linkService.changeStatus(statusDto);
        return flag?ResponseResult.success():ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
    }
    @DeleteMapping ("/{id}")
    public  ResponseResult DeleteLink(@PathVariable long[] id)
    {

        boolean flag=  linkService.Deleteuser(id);
        return flag?ResponseResult.success():ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
    }

}
