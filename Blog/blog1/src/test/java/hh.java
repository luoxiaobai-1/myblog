
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import my.Main;

import my.blog.Constants.SystemConstants;
import my.blog.domain.Vo.ArticleListvo;
import my.blog.domain.entity.Article;
import my.blog.domain.entity.User;
import my.blog.mapper.ArticleMapper;
import my.blog.mapper.CommentMapper;
import my.blog.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;


@SpringBootTest(classes = Main.class)

@Data
public class hh {
    @Autowired
    private ArticleMapper categoryDao;
    @Autowired
    private RedisCache redisCache;
  private   String accessKey;
  private   String secretKey;
  private   String bucket;
    @Test
    public  void a()
    {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
//        String accessKey = "GD1DnkxGoVWj3KIXrrmI4lpVZF01gmjTGP3ifI4b";
//        String secretKey = "gnrG0ToxQ_-o6gw3tM1Xl7UHKVPhu6LsGMq7RReU";
//        String bucket = "luoxiaobai";

//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key =" null";

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            InputStream fileinpty=new FileInputStream("E:\\bizhi\\wallhaven-l8qy8l.jpg");
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(fileinpty,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }

    }
    @Test
    public  void s()
    {
        Page<ArticleListvo> page = new Page<>(1,10);
        List<ArticleListvo> articleListvos=   categoryDao.getArticleList(page,null).getRecords();

        for (ArticleListvo articleListvo : articleListvos) {
            System.out.println(articleListvo.getCreateTime());
        }

    }

}
