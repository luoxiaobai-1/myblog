package my.blog.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Setter;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.exception.SystemException;
import my.blog.service.uploadservice;
import my.blog.utils.PathUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@ConfigurationProperties(prefix = "oss")
@Setter
public class uploadserviceiml implements uploadservice {

    private   String accessKey;
    private   String secretKey;
    private   String bucket;
    private   String webpath;


    @Override
    public ResponseResult uploadimg(MultipartFile img) {

        String originalFilename = img.getOriginalFilename();
        if (!originalFilename.endsWith(".png")&&!originalFilename.endsWith(".jpg"))
        {
            throw new SystemException(HttpCodeEnum.FILE_TYPE_ERROR);
        }
        String path= PathUtils.generateFilePath(originalFilename);
        String url = uploadoss(img,path);

        return ResponseResult.success(url, HttpCodeEnum.SUCCESS);
    }

    private   String uploadoss(MultipartFile img,String path)

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
        String key =path;


        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            InputStream fileinpty=img.getInputStream();
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
return webpath+"/"+key;
    }

}
