package my.controller;

import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.exception.SystemException;
import my.blog.service.uploadservice;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class Uploadweb {
    @Autowired
    private uploadservice uploadservice;

    @PostMapping("/upload")
    public ResponseResult  upload(MultipartFile img)
    {
        try {
            return uploadservice.uploadimg(img);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw  new SystemException(HttpCodeEnum.SYSTEM_ERROR);
        }

    }
}
