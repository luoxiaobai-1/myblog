package my.blog.domain.Dto;

import lombok.Data;

@Data
public class RoleListDto {
    private   Integer pageNum;
    private   Integer pageSize;
    private   String roleName;
    private   String status;
}
