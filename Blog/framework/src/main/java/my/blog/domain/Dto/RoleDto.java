package my.blog.domain.Dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data

public class RoleDto {
@NotNull
 private List<Long> menuIds;



  private String  remark;

    @NotNull(message = "角色关键字不能为空")
   private String  roleKey;
    @NotNull(message = "角色名字为空")
 private  String   roleName;
    @NotNull(message = "排序字符为空")
 private Integer   roleSort;

    @NotNull(message = "状态为空")
 private  String   status;

}
