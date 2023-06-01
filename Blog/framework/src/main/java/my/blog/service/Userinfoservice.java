package my.blog.service;

import my.blog.domain.Vo.Adminuserinfovo;
import my.blog.domain.entity.User;

public interface Userinfoservice {
    public Adminuserinfovo GetuserInfo(User user);
}
