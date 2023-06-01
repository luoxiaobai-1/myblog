package my.controller;

import my.blog.Constants.SystemConstants;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.entity.Comment;
import my.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class Commentweb {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult CommentList(Long articleId,Integer pageNum, Integer pageSize)
    {

        return commentService.GetcommentList(SystemConstants.COMMENT_ARTICLE, articleId, pageNum, pageSize);
    }

    @PostMapping
    public ResponseResult Comment(@RequestBody Comment comment)
    {

        return commentService.setcomment(comment);
    }

    @GetMapping("/linkCommentList")
    public  ResponseResult linkcommentlist(Integer pageNum, Integer pageSize)
    {

        return commentService.GetcommentList(SystemConstants.COMMENT_LINK ,null,pageNum, pageSize);


    }


}
