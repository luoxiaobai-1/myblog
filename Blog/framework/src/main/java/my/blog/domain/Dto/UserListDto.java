package my.blog.domain.Dto;

import lombok.Data;

@Data
public class UserListDto {
    private   Integer pageNum;
    private   Integer pageSize;
    private   String userName;
    private   String status;
    private String phonenumber;

}

