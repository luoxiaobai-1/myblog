package my.blog.domain.Dto;

import lombok.Data;

@Data
public class LinkListDto {
    Integer pageNum;
    Integer pageSize;
    private  String name;
    private  String status;
}
