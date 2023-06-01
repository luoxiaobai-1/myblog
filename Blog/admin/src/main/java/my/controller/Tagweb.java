package my.controller;


import my.blog.domain.Dto.TagListDto;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.Tagvo;
import my.blog.domain.entity.Tag;
import my.blog.service.TagService;
import my.blog.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class Tagweb {
    @Autowired
    private TagService tagService;


    @GetMapping("/list")

    public ResponseResult GetTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto)
    {
        Pagevo gettag = tagService.GettagList(pageNum, pageSize, tagListDto);

        return ResponseResult.success(gettag, HttpCodeEnum.SUCCESS);


    }

   @PostMapping
    public ResponseResult AddTag(@RequestBody TagListDto tagListDto)
    {
        boolean flag= tagService.Addtag(tagListDto);

        return flag?ResponseResult.success():ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);


    }
   @DeleteMapping("/{id}")
    public ResponseResult DelTag(@PathVariable long [] id )
    {
        boolean flag= tagService.Deltag(id);

        return flag?ResponseResult.success():ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);


    }
    @GetMapping("/{id}")
    public ResponseResult GetTag(@PathVariable long id)
    {
        Tagvo tag= tagService.Gettag(id);

        return ResponseResult.success(tag,HttpCodeEnum.SUCCESS);

    }

   @PutMapping()
    public ResponseResult updateTag( @RequestBody Tagvo tagvo )
    {
        tagService.updatetag(tagvo);

        return  ResponseResult.success();

    }
@GetMapping("/listAllTag")
    public ResponseResult GetallTag()
{
 List <Tagvo> tagvos =tagService.getalltag();
    return ResponseResult.success(tagvos,HttpCodeEnum.SUCCESS);

}



}
