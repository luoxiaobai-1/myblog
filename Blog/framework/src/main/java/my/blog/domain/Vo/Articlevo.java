package my.blog.domain.Vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Articlevo {
    /**
     *
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;



    /**
     * 所属分类id
     */
//    @TableField(" category_Id")
    private Long categoryId;
    private String categoryName;



    /**
     * 访问量
     */

    private Long viewCount;

    /**
     * 是否允许评论 1是，0否
     */

    private String isComment;

private  String createBy;

    /**
     *
     */
    private String isTop;
    private Date createTime;
    private List <String> tags;
    private String status;
    private String summary;
    //todo 可能需要重新定义一个vo 来查寻具体文章








}
