package my.blog.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import my.blog.domain.entity.ArticleTag;
import my.blog.mapper.ArticleTagMapper;
import my.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
* @author 14574
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2023-05-06 13:25:49
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService {

}




