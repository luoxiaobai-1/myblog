package my.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.exception.SystemException;
import my.blog.utils.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Component("ps")
@Slf4j
public class permissionservice {

    public boolean hasPermission(String perss)
    {
        boolean flag = false;
        if (SecurityUtils.isAdmin())
        {
            return true;
        }
        else
        {


            List <String> permissions= Objects.requireNonNull(SecurityUtils.getLoginUser()).getPermissions();

                 flag= permissions.contains(perss);

            }



            return flag;


    }
}
