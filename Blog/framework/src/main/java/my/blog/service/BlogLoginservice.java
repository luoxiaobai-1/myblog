package my.blog.service;

import my.blog.domain.Result.ResponseResult;
import my.blog.domain.entity.User;

public interface  BlogLoginservice {

    ResponseResult login(User user);

    ResponseResult logout();
}
