package my.blog.domain.Dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserDto {
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    private String nickName;
    @NotEmpty(message = "密码不能为空")
    private String password;
    /**
     * 账号状态（0正常 1停用）
     */
    @NotEmpty(message = "状态不能为空")
    private String status;
    @NotEmpty(message = "性别不能为空")
    private String sex;
    /**
     * 邮箱
     */
    @Email
    private String email;

    /**
     * 手机号
     */
    @Size(min = 1,max = 11,message ="电话号码不符要求" )
    private String phonenumber;
    @NotEmpty(message = "角色id不能为空")
    private List<Long> roleIds;
    private  long id;
}
