package my.blog.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.blog.domain.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Adminuserinfovo {
    private List<String> permissions;
    private List<String> roles;
    private UserInfoVo user;

}
