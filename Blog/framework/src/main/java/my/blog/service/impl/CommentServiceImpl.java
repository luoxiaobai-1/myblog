package my.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import my.blog.Constants.SystemConstants;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.commentvo;
import my.blog.domain.entity.Comment;
import my.blog.domain.entity.User;
import my.blog.mapper.CommentMapper;
import my.blog.mapper.UserMapper;
import my.blog.service.CommentService;
import my.blog.utils.BeanCopy;
import my.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 14574
* @description 针对表【sg_comment(评论表)】的数据库操作Service实现
* @createDate 2023-04-28 18:28:21
*/
@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {

    @Autowired
    UserMapper userMapper;
    @Override
    public ResponseResult GetcommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper <Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(articleId!=null,Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_STATUS_ROOT);
        queryWrapper.eq(Comment::getType,commentType);

        Page<Comment> page=new Page<>();
        page(page,queryWrapper);

        List <commentvo> commentvos= tocommentvolist(page.getRecords());

        for (commentvo commentvo : commentvos) {
   commentvo.setChildren(getchildrennlist(commentvo.getId()));
            String avater=userMapper.selectById(commentvo.getCreateBy()).getAvatar();
            commentvo.setAvater(avater);
        }

        return ResponseResult.success(new Pagevo(commentvos,page.getTotal()), HttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult setcomment(Comment comment) {

    comment.setCreateBy(SecurityUtils.getUserId());
    comment.setCreateTime(new Date());
    save(comment);


        return ResponseResult.success();
    }



    private List<commentvo> tocommentvolist(List<Comment> commentList)
    {
        List<commentvo> commentvos = BeanCopy.copyBeanList(commentList, commentvo.class);
        for (commentvo commentvo:commentvos)
        {
            User user = userMapper.selectById(commentvo.getCreateBy());
            commentvo.setUsername(user.getNickName());
            if (commentvo.getToCommentUserId()!=-1)
            {
                String ToCommentUsername = userMapper.selectById(commentvo.getToCommentUserId()).getNickName();
                String avater=userMapper.selectById(commentvo.getCreateBy()).getAvatar();
                commentvo.setToCommentUserName(ToCommentUsername);
                commentvo.setAvater(avater);

            }

        }
return commentvos;

    }
    private List<commentvo> getchildrennlist (long id){
        LambdaQueryWrapper <Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByDesc(Comment::getCreateTime);

       return tocommentvolist( list(queryWrapper));



    }

}




