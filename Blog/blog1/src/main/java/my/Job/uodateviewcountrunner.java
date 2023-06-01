package my.Job;

import lombok.extern.slf4j.Slf4j;
import my.blog.Constants.SystemConstants;
import my.blog.domain.entity.Article;
import my.blog.service.ArticleService;
import my.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class uodateviewcountrunner {
    @Autowired
    RedisCache redisCache;
    @Autowired
    ArticleService articleService;
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void viewcount ()
    {
       updateviewcout();
    }



    private void   updateviewcout()
    {
        Map<String, Integer> viewcountMap = redisCache.getCacheMap(SystemConstants.viewcount_Key);
      List<Article> articleS = viewcountMap.entrySet().stream().map(entry -> new Article(Long.parseLong(entry.getKey()), entry.getValue().longValue())).collect(Collectors.toList());
        articleService.updateBatchById(articleS);
    log.info("定时任务已执行");


    }
}
