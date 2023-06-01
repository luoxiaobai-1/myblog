package my.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.blog.domain.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 14574
* @description 针对表【sg_comment(评论表)】的数据库操作Mapper
* @createDate 2023-04-28 18:28:21
* @Entity .entity.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




