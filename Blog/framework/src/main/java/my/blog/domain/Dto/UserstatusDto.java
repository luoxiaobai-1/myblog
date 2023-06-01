package my.blog.domain.Dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserstatusDto {
    @NotEmpty(message = "角色id不能为空")
    private String status;
    @NotNull
    private  long userId;
}
