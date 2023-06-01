package my.blog.service;

import my.blog.domain.Result.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface uploadservice {
    ResponseResult uploadimg(MultipartFile img);
}
