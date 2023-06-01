package my.blog.domain.Dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryListDto {

    Integer pageNum;
    Integer pageSize;
    private  String name;
    private  String status;

}
