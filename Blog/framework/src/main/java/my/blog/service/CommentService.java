package my.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.entity.Comment;

/**
* @author 14574
* @description 针对表【sg_comment(评论表)】的数据库操作Service
* @createDate 2023-04-28 18:28:21
*/
public interface CommentService extends IService<Comment> {

    ResponseResult GetcommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult setcomment(Comment comment);
}
