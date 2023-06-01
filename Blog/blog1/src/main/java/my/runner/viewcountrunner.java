package my.runner;

import lombok.extern.slf4j.Slf4j;
import my.blog.Constants.SystemConstants;
import my.blog.domain.entity.Article;
import my.blog.mapper.ArticleMapper;
import my.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class viewcountrunner implements CommandLineRunner {
   @Autowired
    ArticleMapper articleMapper;
   @Autowired
   RedisCache redisCache;


    @Override
    public void run(String... args) throws Exception {
   updatevuewcount();
    }

    private void updatevuewcount()  {
        List<Article> articles = articleMapper.selectList(null);
     Map<String,Integer> viewcountmap= articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),article -> {
                    return article.getViewCount().intValue();
                }));

        redisCache.setCacheMap(SystemConstants.viewcount_Key,viewcountmap);
        log.info("文章访问量catchmap="+viewcountmap.toString());

    }
}
